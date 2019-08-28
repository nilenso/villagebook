(ns villagebookUI.components.new-org
  (:require [reagent.core :as r]
            [villagebookUI.utils.label :refer [label]]
            [villagebookUI.api.organisation :as org]))


(defn create-org [formdata error creating-org]
  (when (get-in @formdata [:org :name])
    (org/create-org (:org @formdata)
                    (fn [res]
                      (reset! error false)
                      (reset! creating-org false))
                    (fn [res] (reset! error true)))))

(defn new-org [creating-org]
  (let [formdata (r/atom {})
        error    (r/atom false)]
    (fn []
      [:form.inline-block {:on-submit (fn [e]
                                        (.preventDefault e)
                                        (create-org formdata error creating-org))}
       [:div.inline-block [label nil (r/cursor formdata [:org :color])]]
       [:input.new-item-input {:type        "text"
                               :placeholder "New organisation"
                               :on-change   #(swap! formdata assoc-in [:org :name] (-> % .-target .-value))
                               :ref         (fn [el] (if el (.focus el)))
                               :class       (if @error "new-item-error")}]
       [:span.new-item-close {:on-click (fn [] (swap! creating-org not))} "×"]
       [:button {:type "submit" :style {:display "none"}}]])))
