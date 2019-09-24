(ns villagebook.organisation.models
  (:require [villagebook.organisation.db :as db]
            [villagebook.factory :as factory]
            [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]))

(defonce OWNER_PERMISSION :owner)

(defn create!
  [orgdata user-id]
  (let [{org-id :id :as org} (db/create! orgdata)
        permission           (db/add-user-as! org-id user-id OWNER_PERMISSION)]
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
    (if (= (db/get-permission user-id id) (name OWNER_PERMISSION))
      (if-not (empty? (db/delete! id))
        {:success "Organisation deleted"})
      {:error "Invalid permission"})))
