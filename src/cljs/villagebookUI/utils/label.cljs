(ns villagebookUI.utils.label)

(defn random-color []
  (str "hsl(" (->> (.random js/Math)
               (* 360)
               (.floor js/Math)) ", 75%, 80%)"))

(defn label
  ([color]
   [:div.item-color {:style {"backgroundColor" (or color (random-color))}}])
  ([color color-atom]
   (let [init-color! (reset! color-atom (or color (random-color)))]
     (fn []
       [:span
        [:input {:type      "color"
                 :style     {:display "none"}
                 :id        "new-item-color"
                 :value     @color-atom
                 :on-change #(reset! color-atom (-> % .-target .-value))}]
        [:label.item-color.new-item-color  {:for   "new-item-color"
                                            :style {"backgroundColor" @color-atom}}]]))))
