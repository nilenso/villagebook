(ns villagebook.organisation.models
  (:require [villagebook.organisation.db :as db]
            [villagebook.model-helpers :as helpers]
            [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]))

(defn create!
  [orgdata user-id]
  ;; ADD CHECK: IF USER EXISTS
  (let [{org-id :id :as org} (db/create! orgdata)
        permission           (db/add-user-as! org-id user-id helpers/OWNER_PERMISSION)]
    {:success org}))

(defn retrieve
  [id]
  (let [org (db/retrieve id)]
    (if org
      {:success org}
      {:error "Organisation not found"})))

(defn retrieve-by-user
  [user-id]
  {:success (db/retrieve-by-user user-id)})

(defn delete!
  [user-id id]
  "Check if user is owner of the organisation and delete it"
  (jdbc/with-db-transaction [trn (config/db-spec)]
    (if (helpers/is-org-owner? trn id user-id)
      (if-not (empty? (db/delete! trn id))
        {:success "Organisation deleted"}
        {:error "Error in deleting organisation"})
      {:permission-error "Invalid permission"})))
