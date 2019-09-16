(ns villagebook.organisation.models
  (:require [villagebook.organisation.db :as db]
            [villagebook.factory :as factory]))

(defonce DEFAULT_PERMISSION :owner)

(defn create!
  [orgdata user-id]
  (let [{org-id :id :as org} (db/create! orgdata)
        permission           (db/add-user-as! org-id user-id DEFAULT_PERMISSION)]
    {:success org}))

(defn retrieve
  [id]
  (let [org (db/retrieve id)]
    (if org
      {:success org}
      {:error "Organisation not found"})))

(defn retrieve-by-user
  [user-id]
  {:success (vec (db/retrieve-by-user user-id))})
