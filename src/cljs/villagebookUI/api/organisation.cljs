(ns villagebookUI.api.organisation
  [:require [ajax.core :refer [POST]]])

(def ^:private create-org-api "/api/organisations")

(defn create-org [org-data handler-fn error-handler-fn]
  (POST create-org-api
        {:params        org-data
         :format        :raw
         :handler       handler-fn
         :error-handler error-handler-fn}))
