(ns villagebookUI.components.sidebar
  (:require [reagent.core :as r]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.components.create-org :refer [org-creation-form]]
            [villagebookUI.store.organisations :as org-store]
            [villagebookUI.fetchers :as fetchers]))

(defn sidebar []
  (let [creating-org (r/atom false)]
    (fn []
      [:div
       [:div.sidebar
        [:div.text-center.sidebar-logo-box
         [:p.sidebar-logo "villagebook"]
         [:div.divider]]
        [:div.sidebar-section
         [:p.sidebar-section-head "Your organisations"]
         [:ul.sidebar-section-list
          (doall
           (for [org  (org-store/get-all)
                 :let [id (:id org)]]
             [:li.item {:key   id
                        :class (if (= id (:id (org-store/get-selected)))
                                 [:active])}
              [:a.sidebar-link {:href     (str "/orgs/" id)
                                :on-click #(fetchers/fetch-categories! (:id org) first)}
               [utils/patch (:color org)]
               (:name org)]]))
          [:li.item
           {:class [(when @creating-org :hover-disabled)]}
           (if @creating-org
             [org-creation-form #(reset! creating-org false)]
             [:a.sidebar-link {:on-click #(reset! creating-org true)} "+ Create new"])]]]]])))
