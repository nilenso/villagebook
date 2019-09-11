(ns villagebookUI.components.sidebar
  (:require [reagent.core :as r]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.components.new-org :refer [new-org]]
            [villagebookUI.api.organisation :as org]))

(defn sidebar []
  [:div
   [:div.sidebar
    [:div.text-center.sidebar-logo-box
     [:p.sidebar-logo "villagebook"]
     [:div.divider]]
    [:div.sidebar-section
     [:p.sidebar-section-head "Organisations"]
     [:ul.sidebar-section-list
      [:li.item
       [:a.sidebar-link [utils/label "#5fcc5f"] "Org 1"]]
      [:li.item
       [:a.sidebar-link [utils/label "#ff8383"] "Org 2"]]
      [:li.item
       [:form.inline-block
        [:div.inline-block
         [utils/label "#ccc"]]
        [:input.new-item-input {:type "text"
                                :placeholder "New Organisations"}]
        [:span.new-item-close "×"]
        [:button {:type "submit" :style {:display "none"}}]]]]]]])
