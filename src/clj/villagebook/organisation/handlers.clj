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

(defn- retrieve-from-model
  [id]
  (let [message        (models/retrieve id)
        {org :success} message
        {error :error} message]
    (if org
      (res/response org)
      (res/not-found error))))

(defn retrieve
  [request]
  (let [id (edn/read-string (get-in request [:params :id]))]
    (if (s/valid? ::organisation-spec/id id)
      (retrieve-from-model id)
      (res/bad-request "Invalid request."))))

(defn retrieve-by-user
  [request]
  (let [{user-id :id} (:identity request)]
    (res/response (:success (models/retrieve-by-user user-id)))))
