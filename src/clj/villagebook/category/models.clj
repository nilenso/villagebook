(ns villagebook.category.models
  (:require [villagebook.category.db :as db]
            [villagebook.organisation.models :as org-models]
            [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]))

(defn create!
  [name org-id user-id]
  (jdbc/with-db-transaction [trn (config/db-spec)]
    (if (org-models/is-owner-or-member? trn org-id user-id)
      {:success (db/create! trn name org-id)}
      {:error "Permission denied"})))
