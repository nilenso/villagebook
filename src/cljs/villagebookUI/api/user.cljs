(ns villagebookUI.api.user
  [:require [ajax.core :refer [GET]]])

(def ^:private get-user-data-api "/api/user")

(defn get-user-data
  [handler-fn error-handler-fn]
  (GET get-user-data-api
       {:handler       handler-fn
        :error-handler error-handler-fn}))
