(ns villagebookUI.components.main-content
  (:require [villagebookUI.store.organisations :as org-store]
            [villagebookUI.api.organisation :as org-api]
            [villagebookUI.fetchers :as fetchers]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.store.ui :as ui-store]
            [villagebookUI.store.categories :as category-store]
            [villagebookUI.components.content-box :refer [content-box]]
            [villagebookUI.helpers :as helpers]))

(declare navbar delete-org-btn delete-org)

(defn main-content []
  (let [org (org-store/get-selected)]
    [:div.main-content
     [navbar org]
     (if org
       [content-box org
        (category-store/get-by-org (:id org))
        #(fetchers/fetch-categories! (:id org) first)]
       [:div.content-box
        [:div.card
         (if (empty? (org-store/get-all))
           [:h5 "Create a new organisation to get started."]
           [:h5 "Oops, page not found"])]])
     [utils/alert-bottom (ui-store/get-el-state :alert-bottom)]]))

(defn- navbar
  [org]
  [:div.navbar
   [:h5 (:name org)]
   [delete-org-btn org]])

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
    (org-api/delete {:id            (:id org)
                     :handler       (fn [res]
                                      (helpers/show-alert-bottom! :success res)
                                      (fetchers/fetch-orgs! first))
                     :error-handler (fn [res]
                                      (helpers/show-alert-bottom! :error res))})))
