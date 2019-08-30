(ns villagebook.organisation.handlers
  (:require [ring.util.response :as res]
            [clojure.edn :as edn]
            [villagebook.organisation.db :as db]
            [villagebook.organisation.models :as models]
            [villagebook.organisation.spec :as organisation-spec]
            [clojure.spec.alpha :as s]))

(defn create!
  [request]
  (let [{name  :name
         color :color
         :as   orgdata} (:params request)
        {user-id :id}   (:identity request)]
    (if (organisation-spec/valid-organisation-details? orgdata)
      (let [{neworg :success} (models/create! orgdata user-id)]
        (-> (res/response neworg)
            (res/status 201)))
      (res/bad-request "Invalid request."))))

(defn retrieve
  [request]
  (let [id (edn/read-string (get-in request [:params :id]))
        org (db/retrieve id)]
    (if id
      (if org
        (res/response org)
        (res/not-found "Organisation not found."))
      (res/bad-request "Invalid request."))))
