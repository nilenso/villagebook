(ns villagebook.organisation.handlers
  (:require [ring.util.response :as res]
            [clojure.edn :as edn]
            [villagebook.organisation.db :as db]
            [villagebook.organisation.spec :as organisation-spec]
            [clojure.spec.alpha :as s]))

(defn create-organisation
  [request]
  (let [{name :name
         color :color
         :as orgdata} (:params request)]
    (if (organisation-spec/valid-organisation-details? orgdata)
      (let [neworg (db/create orgdata)]
        (-> (res/response neworg)
            (res/status 201)))
      (res/bad-request "Invalid request."))))

(defn get-by-id
  [request]
  (let [id (edn/read-string (get-in request [:params :id]))
        org (db/get-by-id id)]
    (if id
      (if org
        (res/response org)
        (res/not-found "Organisation not found."))
      (res/bad-request "Invalid request."))))
