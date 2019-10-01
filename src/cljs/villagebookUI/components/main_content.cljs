(ns villagebookUI.components.main-content
  (:require [villagebookUI.store.organisations :as org-store]
            [villagebookUI.api.organisation :as org-api]
            [villagebookUI.fetchers :as fetchers]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.components.create-category :refer [create-category-form]]
            [villagebookUI.store.ui :as ui-store]
            [villagebookUI.helpers :as helpers]))

(declare navbar content-box delete-org-btn delete-org)

(defn main-content []
  (let [org (org-store/get-selected)]
    [:div.main-content
     [navbar org]
     (if org
       [content-box]
       [:h5 "Oops, page not found"])
     [utils/alert-bottom (ui-store/get-el-state :alert-bottom)]]))

(defn- navbar
  [org]
  [:div.navbar
   [:h5 (:name org)]
   [delete-org-btn org]])

(defn content-box []
  [:div.content-box
   [:div.category-tabs
    [:a.category-tab {:href "#"} "Category 1"]
    [:a.category-tab {:href "#"} "Category 2"]
    [:a.category-tab.active {:href "#"} "+ Add new"]]
   [:div.simple-card
    [create-category-form]]])

(defn delete-org-btn
  [org]
  (if (= (:permission org) "owner")
    [:a {:href     "#"
         :style    {:height "30px"}
         :on-click #(delete-org org)}
     "Delete organisation"]))

(defn delete-org
  [org]
  (if (js/confirm (str "Are you sure you want to delete " (:name org) "?"))
    (org-api/delete (:id org)
                    (fn [res]
                      (helpers/show-alert-bottom! :success res)
                      (fetchers/fetch-orgs! first))
                    (fn [res]
                      (helpers/show-alert-bottom! :error res)))))
