(ns villagebookUI.components.sidebar
  (:require [reagent.core :as r]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.components.create-org :refer [org-creation-form]]
            [villagebookUI.api.organisation :as org]))

(defn sidebar []
  (let [creating-org (r/atom false)]
    (fn []
      [:div
       [:div.sidebar
        [:div.text-center.sidebar-logo-box
         [:p.sidebar-logo "villagebook"]
         [:div.divider]]
        [:div.sidebar-section
         [:p.sidebar-section-head "Organisations"]
         [:ul.sidebar-section-list
          [:li.item
           [:a.sidebar-link [utils/org-label "#5fcc5f"] "Org 1"]]
          [:li.item
           [:a.sidebar-link [utils/org-label "#ff8383"] "Org 2"]]
          [:li.item
           (if @creating-org
             [org-creation-form #(reset! creating-org false)]
             [:a.sidebar-link {:on-click #(reset! creating-org true)} "+ Create new"])]]]]])))
