(ns villagebookUI.components.dashboard
  (:require [villagebookUI.components.sidebar :refer [sidebar]]
            [villagebookUI.components.main-content :refer [main-content]]))

(defn dashboard []
  (fn []
    [:div
     [sidebar]
     [main-content]]))
