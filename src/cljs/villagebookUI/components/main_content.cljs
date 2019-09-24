(ns villagebookUI.components.main-content
  (:require [villagebookUI.store.organisations :as org-store]
            [villagebookUI.api.organisation :as org-api]
            [villagebookUI.fetchers :as fetchers]))

(declare navbar content-box delete-org-btn delete-org)

(defn main-content []
  (let [org (org-store/get-selected)]
    [:div.main-content
     [navbar org]
     (if org
       [content-box]
       [:h5 "Oops, page not found"])]))

(defn- navbar
  [org]
  [:div.navbar
   [:h5 (:name org)]
   [delete-org-btn org]])

(defn content-box []
  [:div])

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
                    #(fetchers/fetch-orgs! first)
                    identity)))
