(ns villagebook.item.models
  (:require [villagebook.item.db :as item-db]
            [villagebook.field-value.db :as value-db]
            [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]
            [villagebook.category.models :as category-models]))

(defn create!
  [category-id values user-id]
  (jdbc/with-db-transaction [trn (config/db-spec)]
    (if (category-models/is-owner-or-member? category-id user-id)
      ;;TODO: Add check if fields belong to category
      (let [{item-id :id} (item-db/create! trn category-id)]
        {:success {:item-id item-id
                   :fields  (value-db/add-values! trn
                                                  (->> values
                                                       (map #(-> %
                                                                 (assoc :category_id category-id)
                                                                 (assoc :item_id item-id)))))}})
      {:error "Permission denied"})))
