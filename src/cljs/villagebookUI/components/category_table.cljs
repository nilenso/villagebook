(ns villagebookUI.components.category-table
  (:require [reagent.core :as r]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.helpers :refer [handle-enter-esc]]))

(defn create-item [])

(defn new-item-row
  [fields cancel-cb]
  (r/with-let [item (r/atom (reduce #(assoc %1 (:id %2) "") {} fields))]
    [:tr.new-item-tr.hover-disabled
     [:td "1"]
     (for [field fields]
       [:td {:key (:id field)}
        [utils/input
         {:class       [:input :new-item-input :form-control]
          :on-change   #(swap! item assoc (:id field) %)
          :on-key-down #(handle-enter-esc % create-item cancel-cb)
          :autoFocus   (if (= field (first fields))
                         true)}]])]))

(defn new-item-help
  [fields cancel-cb]
  [:tr.hover-disabled
   [:td.tr-help-text {:colSpan (+ 1 (count fields))}
    "Press Enter to submit. Esc to cancel. "
    [:a {:href     "#"
         :on-click cancel-cb} "Cancel"]]])

(defn table-empty-msg
  [fields add-item]
  [:tr.text-center.hover-disabled
   [:td {:colSpan (+ 1 (count fields))}
    "There are no items in this category. "
    [:a {:href     "#"
         :on-click add-item} "Add item."]]])

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
           ^{:key (:id category)}[new-item-row fields #(reset! adding-item false)])
         (if @adding-item
           [new-item-help fields #(reset! adding-item false)])
         (if-not @adding-item
           [:tr.add-item-tr
            [:td.add-item-td {:colSpan (+ 1 (count fields))}
             [:div.add-item-box [:button.btn.btn-primary {:on-click #(reset! adding-item true)} "+ Add item"]]]])]]])))
