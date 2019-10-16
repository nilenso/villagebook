(ns villagebookUI.components.category-table
  (:require [reagent.core :as r]
            [villagebookUI.components.create-item :refer [new-item-row item-help-text]]
            [villagebookUI.components.item-row :refer [item-row]]
            [villagebookUI.store.items :as item-store]))

(declare table-empty-msg)

(defn category-table
  [category on-mount-cb]
  (on-mount-cb)
  (let [adding-item (r/atom false)]
    (fn [category]
      (let [fields (:fields category)
            items  (item-store/get-by-category (:id category))]
        [:div.table-responsive
         [:table.table.table-striped.table-hover.table-bordered
          [:thead.thead-light
           [:tr
            [:th "#"]
            (for [field fields]
              [:th {:key (:id field)} (:name field)])]]
          [:tbody
           (if (and (empty? items)(not @adding-item))
             [table-empty-msg fields #(reset! adding-item true)])
           (map-indexed
            (fn [idx item]
              ^{:key (:id item)}
              [item-row (+ idx 1) item fields]) items)
           (if @adding-item
             ^{:key (:id category)}
             [new-item-row (:id category) fields (+ 1 (count items)) #(reset! adding-item false)])
           (if @adding-item
             [item-help-text fields #(reset! adding-item false)])
           (if-not @adding-item
             [:tr.add-item-row
              [:td {:colSpan (+ 1 (count fields))}
               [:div.add-item-box [:button.btn.btn-primary
                                   {:on-click #(reset! adding-item true)} "+ Add item"]]]])]]]))))

(defn table-empty-msg
  [fields add-item]
  [:tr.text-center.hover-disabled
   [:td {:colSpan (+ 1 (count fields))}
    "There are no items in this category. "
    [:a {:href     "#"
         :on-click add-item} "Add item."]]])
