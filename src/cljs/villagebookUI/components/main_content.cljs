(ns villagebookUI.components.main-content
  (:require [villagebookUI.store.organisations :as org-store]))

(defn main-content []
  [:div.main-content
   [:div.navbar
    [:h5 (:name (org-store/get-selected))]]])
