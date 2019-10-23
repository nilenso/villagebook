(ns villagebookUI.store.items
  (:require [reagent.core :as r]
            [clojure.walk :refer [keywordize-keys]]
            [villagebookUI.store.state :refer [state]]))

(def items
  (r/cursor state [:items]))

(defn- index-by
  [f coll]
  (reduce #(assoc %1 (f %2) %2) {} coll))

(defn add-all-to-category!
  [category-id table-data]
  (->> (:items (keywordize-keys table-data))
       (reduce
        (fn [item-list item]
          (assoc item-list (:id item) {:id     (:id item)
                                       :values (index-by :field_id (:values item))}))
        {}) ;; reorg response JSON
       (swap! items assoc category-id)))

(defn get-by-category
  [category-id]
  (get @items category-id))

(defn update-value!
  [item-id category-id field-id value]
  (swap! items assoc-in [category-id item-id :values field-id :value] value))
