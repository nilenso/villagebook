(ns villagebookUI.api.user
  (:require [ajax.core :refer [GET]]
            [villagebookUI.store.user :as store]))

(def ^:private get-user-data-api "/api/user")

(defn get-data!
  []
  (GET get-user-data-api
       {:handler          (fn [res]
                            (store/add! res))
        :error-handler    (fn [res]
                            (store/add! nil))
        :finally          #(store/fetched!)}))
