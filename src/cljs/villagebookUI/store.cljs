(ns villagebookUI.store
  (:require [reagent.core :as r]))

(defonce state (r/atom nil))

(def user
  (r/cursor state [:user]))

(defn add-user! [userdata]
  (swap! state assoc :user userdata))

(defn fetched? []
  (:fetched @state))

(defn fetched! []
  (swap! state assoc :fetched true))

(defn init []
  (reset! state {:user    {}
                 :fetched false}))
