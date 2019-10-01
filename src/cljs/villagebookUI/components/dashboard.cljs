(ns villagebookUI.components.dashboard
  (:require [villagebookUI.components.sidebar :refer [sidebar]]
            [villagebookUI.components.main-content :refer [main-content]]))

(defn dashboard [on-mount-cb]
  (on-mount-cb)
  (fn []
    [:div
     [sidebar]
     [main-content]]))
