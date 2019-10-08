(ns villagebookUI.components.create-org
  (:require [reagent.core :as r]
            [villagebookUI.helpers :refer [random-color handle-esc]]
            [villagebookUI.fetchers :as fetchers]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.api.organisation :as org-api]))


(defn create-org!
  [org success-handler error-handler]
  (when (:name org)
    (org-api/create {:org-data      org
                     :handler       (fn [res]
                                      (success-handler)
                                      (fetchers/fetch-orgs! last))
                     :error-handler error-handler})))

(defn org-creation-form [on-close-handler]
  (let [color (random-color)
        form  (r/atom {:name  nil
                       :color color})
        error (r/atom false)]
    (fn []
      [:form.inline-block {:on-submit (fn [e]
                                        (.preventDefault e)
                                        (create-org! @form
                                                     on-close-handler
                                                     #(reset! error true)))}
       [:div.inline-block
        [utils/color-picker-patch
         {:init-color color
          :on-change  #(swap! form assoc :color %)}]]
       [utils/input {:type        "text"
                     :placeholder "New organisation"
                     :class       [:new-item-input
                                   (if @error :new-item-error)]
                     :ref         (fn [el] (if el (.focus el)))
                     :on-change   #(swap! form assoc :name %)
                     :on-key-down #(handle-esc % on-close-handler)}]
       [:span.new-item-close {:on-click on-close-handler} "×"]
       [:button {:type "submit" :style {:display "none"}}]])))
