(ns villagebookUI.store.core
  (:require [villagebookUI.store.user :as user-store]
            [villagebookUI.store.session :as session]
            [villagebookUI.store.organisations :as org-store]))

(defn init! []
  (session/init!)
  (user-store/init!)
  (org-store/init!))
