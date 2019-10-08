(ns villagebook.category.models
  (:require [villagebook.category.db :as db]
            [villagebook.field.db :as field-db]
            [villagebook.field.models :as field-models]
            [villagebook.model-helpers :as helpers]
            [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]))

(defn create!
  [name fields org-id user-id]
  (jdbc/with-db-transaction [trn (config/db-spec)]
    (if (helpers/is-org-owner-or-member? trn org-id user-id)
      (let [category       (db/create! trn name org-id)
            created-fields (field-models/create-fields! trn (:id category) fields)]
        {:success (assoc category :fields created-fields)})
      {:error "Permission denied"})))

(defn retrieve-by-org
  [org-id user-id]
  (if (helpers/is-org-owner-or-member? org-id user-id)
    (let [categories (db/retrieve-by-org org-id)
          fields     (group-by :category-id (field-db/retrieve-by-org org-id))]
      {:success (map #(assoc % :fields (get fields (:id %))) categories)})
    {:error "Permission denied"}))
