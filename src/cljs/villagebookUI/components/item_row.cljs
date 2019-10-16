(ns villagebookUI.components.item-row
  (:require [clojure.string :refer [blank?]]))

(defn item-row
  [row-number item fields]
  [:tr
   [:td row-number]
   (for [field-id (map #(:id %) fields)]
     (let [value (get-in item [:values field-id :value])
           id    (get-in item [:values field-id :id])]
       ^{:key id}
       [:td (if (blank? value)
              "-"
              value)]))])
