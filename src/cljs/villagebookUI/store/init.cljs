(ns villagebookUI.store.init
  (:require [villagebookUI.store.user :as user-store]
            [villagebookUI.store.organisations :as org-store]))

(defn init! []
  (user-store/init!)
  (org-store/init!))
