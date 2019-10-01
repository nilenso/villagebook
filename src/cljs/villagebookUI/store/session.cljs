(ns villagebookUI.store.session
  (:require [reagent.core :as r]
            [villagebookUI.store.state :refer [state]]))

(def session
  (r/cursor state [:session]))

(defn get-session []
  @session)

(defn set!
  [page]
  (reset! session page))

(defn current-page []
  (:current-page @session))

(defn route-params []
  (:route-params @session))

(defn init! []
  (reset! session {:current-page nil
                   :route-params nil}))
