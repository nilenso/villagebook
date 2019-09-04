(ns villagebookUI.components.sidebar
  (:require [clojure.walk :refer [keywordize-keys]]
            [reagent.core :as r]
            [villagebookUI.store.organisations :as org-store]
            [villagebookUI.utils.label :refer [label]]
            [villagebookUI.components.new-org :refer [new-org]]
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
          (doall
           (for [org (org-store/get-all)]
             [:li.item {:key   (:id org)
                        :class (when (= (:id org)(:id (org-store/get-current))) "active")}
              [:a.sidebar-link {:href (str "/orgs/" (:id org))}
               [label (:color org)]
               (:name org)]]))
          [:li.item
           (if @creating-org
             [new-org creating-org]
             [:a.sidebar-link {:on-click (fn [] (swap! creating-org not))} "+ Create new"])]]]]])))
