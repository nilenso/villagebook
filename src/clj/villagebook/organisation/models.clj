(ns villagebook.organisation.models
  (:require [villagebook.organisation.db :as db]
            [villagebook.factory :as factory]))

(defonce DEFAULT_PERMISSION "owner")

(defn create!
  [orgdata user-id]
  (let [{org-id :id :as org} (db/create! orgdata)
        permission           (db/add-user-as! org-id user-id DEFAULT_PERMISSION)]
    {:success org}))