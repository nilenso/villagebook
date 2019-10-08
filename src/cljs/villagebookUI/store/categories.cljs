(ns villagebookUI.store.categories
  (:require [reagent.core :as r]
            [clojure.walk :refer [keywordize-keys]]
            [villagebookUI.store.state :refer [state]]))

(def categories
  (r/cursor state [:categories]))

(def selected-category
  (r/cursor state [:selected-category]))

(defn add-all! [org-id category-list]
  (swap! categories assoc org-id (map #(keywordize-keys %) category-list)))

(defn add-one! [org-id category]
  (swap! categories update org-id conj (keywordize-keys category)))

(defn get-by-org [org-id]
  (get @categories org-id))

(defn set-selected! [category]
  (reset! selected-category (keywordize-keys category)))

(defn get-selected []
  @selected-category)

(defn init! []
  (reset! categories {}))
