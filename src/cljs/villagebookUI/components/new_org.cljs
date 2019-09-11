(ns villagebookUI.components.new-org
  (:require [reagent.core :as r]
            [villagebookUI.helpers :refer [random-color handle-esc]]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.api.organisation :as org]))

(defn create-org [form success-handler error-handler]
  (when (:name form)
    (org/create-org form
                    success-handler
                    error-handler)))

(defn new-org [on-close-handler]
  (let [form  (r/atom {:name  nil
                       :color (random-color)})
        color (r/cursor form [:color])
        error (r/atom false)]
    (fn []
      [:form.inline-block {:on-submit (fn [e]
                                        (.preventDefault e)
                                        (create-org @form
                                                    on-close-handler
                                                    #(reset! error true)))}
       [:div.inline-block
        [utils/label @color #(swap! form assoc :color %)]]
       [utils/input {:type        "text"
                     :placeholder "New organisation"
                     :class       [:new-item-input
                                   (if @error :new-item-error)]
                     :ref         (fn [el] (if el (.focus el)))
                     :on-change   #(swap! form assoc :name %)
                     :on-key-down #(handle-esc % on-close-handler)}]
       [:span.new-item-close {:on-click on-close-handler} "×"]
       [:button {:type "submit" :style {:display "none"}}]])))
