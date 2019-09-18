(ns villagebookUI.store.organisations
  (:require [reagent.core :as r]
            [clojure.walk :refer [keywordize-keys]]
            [villagebookUI.store.state :refer [state]]))

(def organisations
  (r/cursor state [:organisations]))

(defn get-all []
  @organisations)

(defn add-all! [orgs]
  (reset! organisations (map #(keywordize-keys %) orgs)))

(defn add-one! [org]
  (swap! organisations conj (keywordize-keys org)))

(defn init! []
  (reset! organisations {}))
