(ns villagebookUI.components.utils)

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

(defn label
  ([color]
   [:div.item-label {:style {"backgroundColor" color}}])
  ([color on-change-handler]
   [:span
    [input {:type      "color"
            :id        "new-item-label"
            :style     {:display "none"}
            :value     color
            :on-change #(on-change-handler %)}]
    [:label.item-label.new-item-label  {:for   "new-item-label"
                                        :style {"backgroundColor" color}}]]))
