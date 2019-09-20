(ns villagebookUI.api.organisation
  [:require [ajax.core :refer [GET POST]]])

(def ^:private create-org-api "/api/organisations")
(def ^:private get-all-orgs-api "/api/organisations")

(defn create! [org-data handler error-handler]
  (POST create-org-api
        {:params        org-data
         :format        :raw
         :handler       handler
         :error-handler error-handler}))

(defn get-all [handler error-handler]
  (GET get-all-orgs-api
       {:handler         handler
        :response-format :json
        :error-handler   error-handler}))
