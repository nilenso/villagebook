(ns villagebook.item.models
  (:require [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]
            [villagebook.model-helpers :as helpers]
            [villagebook.field-value.db :as value-db]
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
