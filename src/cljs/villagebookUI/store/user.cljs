(ns villagebookUI.store.user
  (:require [reagent.core :as r]
            [villagebookUI.store.core :as store]))

(def user
  (r/cursor store/state [:user]))

(defn add! [userdata]
  (swap! user assoc :data userdata))

(defn fetched! []
  (swap! user assoc :fetched true))

(defn fetching! []
  (swap! user assoc :fetched false))

(defn fetched? []
  (:fetched @user))

(defn init! []
  (reset! user {:fetched false}))

(defn get []
  (:data @user))
