(ns villagebookUI.components.item-row
  (:require [reagent.core :as r]
            [villagebookUI.helpers :as helpers]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.store.categories :as category-store]
            [villagebookUI.store.organisations :as org-store]
            [villagebookUI.store.items :as item-store]
            [villagebookUI.api.item :as item-api]))

(defn- update-item!
  [item-id values]
  (item-api/update-item {:org-id        (:id (org-store/get-selected))
                         :category-id   (:id (category-store/get-selected))
                         :item          {:item-id item-id
                                         :values  (vals values)}
                         :handler       #(helpers/show-alert-bottom! :success "Item updated")
                         :error-handler #(helpers/show-alert-bottom! :error (:response %))}))

(defn remove-focus
  [id]
  (.blur (.getElementById js/document id)))

(defn item-row
  [row-number item-id category-id fields]
  (r/with-let [init-values (get-in (item-store/get-by-category category-id) [item-id :values])]
    (let [values (get-in (item-store/get-by-category category-id) [item-id :values])]
      [:tr.item-row
       [:td.item-text row-number]
       (for [field-id (map #(:id %) fields)
             :let     [value (get values field-id)]]
         ^{:key (:id value)}
         [:td
          [utils/input
           {:id          (:id value)
            :class       [:editable :form-control]
            :value       (:value value)
            :placeholder "-"
            :on-change   #(item-store/update-value! item-id category-id field-id %)
            :on-key-down (fn [e]
                           (helpers/handle-enter-esc e
                                                     #(do
                                                        (update-item! item-id values)
                                                        (remove-focus (:id value)))
                                                     #(do
                                                        (item-store/update-value! item-id
                                                                                  category-id
                                                                                  field-id
                                                                                  (get-in init-values [field-id :value]))
                                                        (remove-focus (:id value)))))}]])])))
