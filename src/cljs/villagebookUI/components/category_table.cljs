(ns villagebookUI.components.category-table
  (:require [reagent.core :as r]
            [villagebookUI.components.create-item :refer [new-item-row item-help-text]]))

(declare table-empty-msg)

(defn category-table
  [category]
  (r/with-let [adding-item (r/atom false)]
    (let [fields (:fields category)]
      [:div.table-responsive
       [:table.table.table-striped.table-hover.table-bordered
        [:thead.thead-light
         [:tr
          [:th "#"]
          (for [field fields]
            [:th {:key (:id field)} (:name field)])]]
        [:tbody
         (if-not @adding-item
           [table-empty-msg fields #(reset! adding-item true)])
         (if @adding-item
           ^{:key (:id category)}[new-item-row (:id category) fields #(reset! adding-item false)])
         (if @adding-item
           [item-help-text fields #(reset! adding-item false)])
         (if-not @adding-item
           [:tr.add-item-row
            [:td {:colSpan (+ 1 (count fields))}
             [:div.add-item-box [:button.btn.btn-primary
                                 {:on-click #(reset! adding-item true)} "+ Add item"]]]])]]])))

(defn table-empty-msg
  [fields add-item]
  [:tr.text-center.hover-disabled
   [:td {:colSpan (+ 1 (count fields))}
    "There are no items in this category. "
    [:a {:href     "#"
         :on-click add-item} "Add item."]]])
