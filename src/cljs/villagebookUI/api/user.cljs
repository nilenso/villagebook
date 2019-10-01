(ns villagebookUI.api.user
  (:require [ajax.core :refer [GET]]
            [villagebookUI.store.user :as store]))

(def ^:private get-user-data-api "/api/user")

(defn get-data [handler error-handler finally-fn]
  (GET get-user-data-api
       {:handler       handler
        :error-handler error-handler
        :finally       finally-fn}))
