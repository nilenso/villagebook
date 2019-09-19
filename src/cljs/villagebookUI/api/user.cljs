(ns villagebookUI.api.user
  (:require [ajax.core :refer [GET]]
            [villagebookUI.store.user :as store]))

(def ^:private get-user-data-api "/api/user")

(defn get-data [handler-fn error-handler-fn finally-fn]
  (GET get-user-data-api
       {:handler         handler-fn
        :error-handler   error-handler-fn
        :finally finally-fn}))
