(ns villagebookUI.components.create-category
  (:require [reagent.core :as r]
            [villagebookUI.helpers :as helpers]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.fetchers :as fetchers]
            [villagebookUI.api.category :as api]
            [villagebookUI.store.organisations :as org-store]))

(defn submit-create-category
  [name fields]
  (let [org-id (:id (org-store/get-selected))]
    (api/create {:name   name
                 :fields (map #(second %) fields)}
                org-id
                #(do
                   (fetchers/fetch-categories! org-id last)
                   (helpers/show-alert-bottom! :success (str "Category " name " created")))
                #(helpers/show-alert-bottom! :error (:response %)))))

(defn new-field-key
  [fields]
  (-> fields last first inc))

(def new-field
  {:name "New column"})

(defn create-category-form []
  (let [name   (r/atom "")
        fields (r/atom (array-map 1 new-field))]
    (fn []
      [:div
       [:form.form-group
        {:on-submit #(do
                       (.preventDefault %)
                       (submit-create-category @name @fields))}
        [utils/input {:type        "text"
                      :class       [:create-category-name]
                      :placeholder "Category name"
                      :on-change   #(reset! name %)}]
        [:div.input-group.mb-3
         [:div.input-group-prepend
          [:span.input-group-text "Properties"]]
         (doall
          (for [[k field] @fields]
            [utils/input {:key         k
                          :type        "text"
                          :class       [:form-control]
                          :style       {:width "150px" :flex :none}
                          :placeholder "Column name"
                          :value       (:name field)
                          :on-change   #(swap! fields assoc k {:name %})}]))
         [:div.input-group-append
          [:a.btn.btn-outline-secondary
           {:on-click #(swap! fields assoc (new-field-key @fields) new-field)}
           "+"]]]
        [:button {:type "submit" :class [:btn :btn-outline-primary]} "Create"]]])))
