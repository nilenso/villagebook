(ns villagebookUI.store.core
  (:require [villagebookUI.store.user :as user-store]
            [villagebookUI.store.organisations :as org-store]
            [villagebookUI.api.user :as user-api]))

(defn init! []
  (user-store/init!)
  (org-store/init!)
  (user-api/get-data!))
