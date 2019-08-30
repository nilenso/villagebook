(ns villagebookUI.store.init
  (:require [villagebookUI.store.user :as user-store]))

(defn init! []
  (user-store/init!))
