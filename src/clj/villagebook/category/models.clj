(ns villagebook.category.models
  (:require [villagebook.category.db :as db]
            [villagebook.field.models :as field-models]
            [villagebook.organisation.models :as org-models]
            [clojure.java.jdbc :as jdbc]
            [villagebook.category.spec :as spec]
            [villagebook.config :as config]))

(defn create!
  [name fields org-id user-id]
  (jdbc/with-db-transaction [trn (config/db-spec)]
    (if (org-models/is-owner-or-member? trn org-id user-id)
      (let [category (db/create! trn name org-id)]
         (field-models/create-fields! trn (:id category) fields)
         {:success category})
      {:error "Permission denied"})))
