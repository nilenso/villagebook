(ns villagebookUI.components.sidebar
  (:require [reagent.core :as r]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.components.create-org :refer [org-creation-form]]
            [villagebookUI.store.organisations :as org-store]))

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
          (for [org (org-store/get-all)]
            [:li.item {:key (:id org)}
             [:a.sidebar-link {:href "#"}
              [utils/patch (:color org)]
              (:name org)]])
          [:li.item
           (if @creating-org
             [org-creation-form #(reset! creating-org false)]
             [:a.sidebar-link {:on-click #(reset! creating-org true)} "+ Create new"])]]]]])))
