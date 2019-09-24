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
        user-id         (get-in request [:identity :id])]
    (if (organisation-spec/valid-organisation-details? orgdata)
      (let [{neworg :success} (models/create! orgdata user-id)]
        (-> (res/response neworg)
            (res/status 201)))
      (res/bad-request "Invalid request."))))

(defn- retrieve-from-model
  [id]
  (let [message (models/retrieve id)
        org     (:success message)
        error   (:error message)]
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
  (let [user-id (get-in request [:identity :id])]
    (res/response (:success (models/retrieve-by-user user-id)))))

(defn- delete-send-response
  [user-id org-id]
  (let [message (models/delete! user-id org-id)
        success (:success message)]
    (if success
      (res/response success)
      (-> (res/response (:error message))
          (res/status 403)))))

(defn delete!
  [request]
  (let [user-id (get-in request [:identity :id])
        id      (edn/read-string (get-in request [:params :id]))]
    (if (s/valid? ::organisation-spec/id id)
      (delete-send-response user-id id)
      (res/bad-request "Invalid request."))))
