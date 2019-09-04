(ns villagebookUI.components.dashboard
  (:require [reagent.session :as session]
            [villagebookUI.store.organisations :as org-store]
            [villagebookUI.api.organisation :as org]
            [villagebookUI.components.sidebar :refer [sidebar]]
            [villagebookUI.components.main-content :refer [main-content]]))

(defn get-org-from-url []
  (let [org-id (get-in (session/get :route) [:route-params :org-id])]
    (-> (filter #(= (:id %) (js/parseInt org-id))(org-store/get-all))
        first)))

(defn fetch-organisations! []
  (org/get-all
   (fn [res]
     (org-store/add-all! res))
   identity))

(defn dashboard []
  (fetch-organisations!)
  (let []
    (fn []
      (org-store/set-current! (or
                               (get-org-from-url)
                               (first (org-store/get-all))))
      [:div
       [sidebar]
       [main-content]])))
