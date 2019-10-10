(ns villagebookUI.store.ui
  (:require [reagent.core :as r]
            [villagebookUI.store.state :refer [state]]))

(def ui-state
  (r/cursor state [:ui]))

(defn get-el-state [element-key]
  (get @ui-state element-key))

(defn set! [element-key val]
  (swap! ui-state assoc element-key val))

(defn init! []
  (reset! ui-state {}))
