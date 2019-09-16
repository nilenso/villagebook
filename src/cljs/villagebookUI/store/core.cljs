(ns villagebookUI.store.core
  (:require [villagebookUI.store.user :as user-store]
            [villagebookUI.api.user :as user-api]))

(defn init! []
  (user-store/init!)
  (user-api/get-data!))
