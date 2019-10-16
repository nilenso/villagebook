(ns villagebookUI.components.create-item
  (:require [reagent.core :as r]
            [villagebookUI.helpers :as helpers]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.fetchers :as fetchers]
            [villagebookUI.store.organisations :as org-store]
            [villagebookUI.api.item :as item-api]))

(defn- create-item!
  [category-id item close-cb]
  (let [org-id (:id (org-store/get-selected))]
    (item-api/create {:org-id        org-id
                      :category-id   category-id
                      :item          {:item (map (fn [[k v]] {:field_id k
                                                              :value    v}) item)}
                      :handler       #(do (close-cb)
                                          (fetchers/fetch-items! org-id category-id)
                                          (helpers/show-alert-bottom! :success "Item added"))
                      :error-handler #(helpers/show-alert-bottom! :error (:response %))})))

(defn new-item-row
  [category-id fields row-number close-cb]
  (r/with-let [item (r/atom (reduce #(assoc %1 (:id %2) "") {} fields))]
    [:tr.new-item-row.hover-disabled
     [:td row-number]
     (for [field fields]
       [:td {:key (:id field)}
        [utils/input
         {:class       [:input :new-item-input :form-control]
          :on-change   #(swap! item assoc (:id field) %)
          :on-key-down (fn [e]
                         (helpers/handle-enter-esc e
                                           #(create-item! category-id
                                                         @item
                                                         close-cb)
                                           close-cb))
          :autoFocus   (if (= field (first fields)) true)}]])]))

(defn item-help-text
  [fields cancel-cb]
  [:tr.hover-disabled
   [:td.tr-help-text {:colSpan (+ 1 (count fields))}
    "Press Enter to submit. Esc to cancel. "
    [:a {:href     "#"
         :on-click cancel-cb} "Cancel"]]])
