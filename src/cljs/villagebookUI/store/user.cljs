(ns villagebookUI.store.user
  (:require [reagent.core :as r]
            [clojure.walk :refer [keywordize-keys]]
            [villagebookUI.store.state :refer [state]]))

(def user
  (r/cursor state [:user]))

(defn add! [userdata]
  (swap! user assoc :data (keywordize-keys userdata)))

(defn read []
  (:data @user))

(defn fetched! []
  (swap! user assoc :fetched true))

(defn fetching! []
  (swap! user assoc :fetched false))

(defn fetched? []
  (:fetched @user))

(defn init! []
  (reset! user {:data    {}
                :fetched false}))
