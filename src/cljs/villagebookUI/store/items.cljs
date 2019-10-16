(ns villagebookUI.store.items
  (:require [reagent.core :as r]
            [clojure.walk :refer [keywordize-keys]]
            [villagebookUI.store.state :refer [state]]))

(def items
  (r/cursor state [:items]))

(defn add-all-to-category! [category-id table-data]
  (let [item-list (for [item (:items (keywordize-keys table-data))]
                    {:id     (:id item)
                     :values (reduce #(assoc %1 (:field_id %2) %2) {} (:values item))})] ;; reorg response JSON
    (swap! items assoc category-id item-list)))

(defn get-by-category [category-id]
  (get @items category-id))
