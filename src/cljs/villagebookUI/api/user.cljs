(ns villagebookUI.api.user
  (:require [ajax.core :refer [GET]]
            [villagebookUI.store.user :as store]))

(def ^:private get-user-data-api "/api/user")

(defn get-data [{:keys [handler error-handler finally]}]
  (GET get-user-data-api
       {:handler       handler
        :error-handler error-handler
        :finally       finally}))
