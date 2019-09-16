(ns villagebookUI.store
  (:require [reagent.core :as r]
            [villagebookUI.api.user :as user-api]))

(defonce state (r/atom nil))

(def user
  (r/cursor state [:user]))

(defn add-user! [userdata]
  (swap! state assoc :user userdata))

(defn fetched? []
  (:fetched @state))

(defn fetched! []
  (swap! state assoc :fetched true))

(defn fetch-user!
  []
  (user-api/get-user-data
   (fn [res]
     (add-user! res))
   (fn [res]
     (add-user! nil))
   #(fetched!)))

(defn init []
  (reset! state {:user    {}
                 :fetched false})
  (fetch-user!))
