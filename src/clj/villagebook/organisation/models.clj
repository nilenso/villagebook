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

(defn is-owner?
  ([org-id user-id]
   (is-owner? (config/db-spec) org-id user-id))
  ([conn org-id user-id]
   (= (db/get-permission conn user-id org-id) (name OWNER_PERMISSION))))

(defn delete!
  [user-id id]
  "Check if user is owner of the organisation and delete it"
  (jdbc/with-db-transaction [trn (config/db-spec)]
    (if (is-owner? trn id user-id)
      (if-not (empty? (db/delete! trn id))
        {:success "Organisation deleted"})
      {:error "Invalid permission"})))
