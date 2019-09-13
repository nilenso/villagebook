(ns villagebookUI.components.utils
  (:require [reagent.core :as r]))

(defn loading
  []
  [:div.loading-mask
   [:div.loading-mask-center
    [:span.large-blue-spinner]]])

(defn- assoc-target-value
  [attributes event-name]
  (if-let [event (event-name attributes)]
    (assoc attributes event-name #(event (-> % .-target .-value)))
    attributes))

(defn input
  [attributes]
  [:input (assoc-target-value attributes :on-change)])

(defn patch
  [color]
  [:div.item-label {:style {"backgroundColor" color}}])

(defn color-picker-patch
  [{:keys [init-color on-change]}]
  (let [color (r/atom init-color)]
    (fn []
      [:span
       [input {:type      "color"
               :id        "new-item-label"
               :style     {:display "none"}
               :on-change #(do
                             (if on-change
                               (on-change %))
                             (reset! color %))}]
       [:label.item-label.new-item-label  {:for   "new-item-label"
                                           :style {"backgroundColor" @color}}]])))
