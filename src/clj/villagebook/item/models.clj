(ns villagebook.item.models
  (:require [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]
            [villagebook.model-helpers :as helpers]
            [villagebook.field-value.db :as value-db]
            [villagebook.field.db :as field-db]
            [villagebook.item.db :as item-db]))

(defn create!
  [category-id values user-id]
  (jdbc/with-db-transaction [trn (config/db-spec)]
    (if (helpers/is-category-owner-or-member? category-id user-id)
      ;;TODO: Add check if fields belong to category
      ;;Create empty field if value not supplied for a category(?)
      (let [{item-id :id} (item-db/create! trn category-id)]
        (value-db/add-values! trn (->> values
                                       (map #(assoc % :category_id category-id
                                                      :item_id item-id))))
        {:success {:id item-id}})
      {:error "Permission denied"})))

(defn retrieve
  [category-id id user-id]
  (if (helpers/is-category-owner-or-member? category-id user-id)
    (if-let [values (value-db/retrieve-by-item id)]
      {:success {:id     id
                 :values values}})
    {:error "Permission denied"}))

(defn- group-by-item
  [values]
  (->> values
       (group-by :item-id)
       (map (fn [[k v]]
              {:id     k
               :values (map #(dissoc % :item-id) v)}))))

(defn retrieve-by-category
  [category-id user-id]
  "Retrieves all items in a category alongwith the category schema"
  (jdbc/with-db-transaction [trn (config/db-spec)]
    (if (helpers/is-category-owner-or-member? category-id user-id)
      {:success {:fields (field-db/retrieve-by-category category-id)
                 :items  (-> category-id
                             value-db/retrieve-by-category
                             group-by-item)}}
      {:error "Permission denied"})))

(defn update!
  [item-id values]
  (jdbc/with-db-transaction [trn (config/db-spec)]
    ;; TODO: Check if user can update item
    (doseq [value values]
      (value-db/update! item-id
                        (:field_id value)
                        (:value value)))
    {:success {:values (value-db/retrieve-by-item trn item-id)}}))
