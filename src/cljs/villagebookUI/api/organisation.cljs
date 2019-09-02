(ns villagebookUI.api.organisation
  [:require [ajax.core :refer [GET POST]]])

(def ^:private create-org-api "/api/organisations")
(def ^:private get-orgs-api "/api/organisations")

(defn create-org [org-data handler-fn error-handler-fn]
  (POST create-org-api
        {:params        org-data
         :format        :raw
         :handler       handler-fn
         :error-handler error-handler-fn}))

(defn get-all [handler-fn error-handler-fn]
  (GET get-orgs-api
       {:handler         handler-fn
        :response-format :json
        :error-handler   error-handler-fn}))
