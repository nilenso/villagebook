(ns villagebook.organisation.handlers
  (:require [ring.util.response :as res]
            [clojure.edn :as edn]
            [villagebook.utils :as utils]
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

(defn retrieve
  [request]
  (let [id (utils/string->int (get-in request [:params :org-id]))]
    (if (s/valid? ::organisation-spec/id id)
      (utils/model-to-http {:message        (models/retrieve id)
                            :response-codes {:success 200
                                             :error   404}})
      (res/bad-request "Invalid request."))))

(defn retrieve-by-user
  [request]
  (let [user-id (get-in request [:identity :id])]
    (res/response (:success (models/retrieve-by-user user-id)))))

(defn delete!
  [request]
  (let [user-id (get-in request [:identity :id])
        id      (utils/string->int (get-in request [:params :org-id]))]
    (if (s/valid? ::organisation-spec/id id)
      (utils/model-to-http {:message        (models/delete! user-id id)
                            :response-codes {:success          200
                                             :permission-error 403
                                             :error            500}})
      (res/bad-request "Invalid request."))))
