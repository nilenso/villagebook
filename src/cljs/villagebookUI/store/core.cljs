(ns villagebookUI.store.core
  (:require [villagebookUI.store.user :as user-store]
            [villagebookUI.store.session :as session]
            [villagebookUI.store.organisations :as org-store]
            [villagebookUI.store.ui :as ui]))

(defn init! []
  (session/init!)
  (ui/init!)
  (user-store/init!)
  (org-store/init!))
