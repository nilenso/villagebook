(ns villagebookUI.components.content-box
  (:require [reagent.core :as r]
            [villagebookUI.components.create-category :refer [create-category-form]]
            [villagebookUI.components.category-table :refer [category-table]]
            [villagebookUI.fetchers :as fetchers]
            [villagebookUI.store.categories :as category-store]))

(defn- category-selected?
  [category categories]
  (or (= (category-store/get-selected) category)
      (empty? categories)))

(defn category-tab
  [categories category name]
  [:a.category-tab {:href     "#"
                    :key      (:id category)
                    :on-click #(category-store/set-selected! category)
                    :class    [(if (category-selected? category categories)
                                 :active)]}
   name])

(defn category-tabs [categories]
  [:div.category-tabs
   (doall
    (for [category categories]
      [category-tab categories category (:name category)]))
   [category-tab categories :new "+ Add new"]])

(defn content-box
  [org categories on-mount-cb]
  (on-mount-cb)
  (fn [org categories]
    [:div.content-box
     [category-tabs categories]
     [:div.simple-card
      (if (category-selected? :new categories)
        [create-category-form]
        [category-table (category-store/get-selected)])]]))
