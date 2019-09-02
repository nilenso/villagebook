(ns villagebookUI.components.main-content
  (:require [villagebookUI.store.organisations :as org-store]))

(defn main-content []
  (let []
    (fn []
      [:div.main-content
       [:div.navbar]
       [:div
        [:h3 (:name (org-store/get-current))]]])))
