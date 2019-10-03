(ns villagebook.field.models
  (:require [villagebook.field.db :as db]
            [villagebook.config :as config]))

(defn create-fields!
  ([category-id fields]
   (create-fields! (config/db-spec) category-id fields))
  ([trn category-id fields]
   (let [field-rows (map #(assoc % :category_id category-id) fields)
         categories (db/create-fields! trn field-rows)]
     {:success categories})))
