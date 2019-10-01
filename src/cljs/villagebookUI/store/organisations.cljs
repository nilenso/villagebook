(ns villagebookUI.store.organisations
  (:require [reagent.core :as r]
            [clojure.walk :refer [keywordize-keys]]
            [villagebookUI.helpers :as helpers]
            [villagebookUI.store.state :refer [state]]))

(def organisations
  (r/cursor state [:organisations]))

(def selected-organisation
  (r/cursor state [:selected-organisation]))

(defn get-all []
  @organisations)

(defn get-by-id [id]
  (some #(if (= (:id %) id) %) (get-all)))

(defn get-selected []
  (or (get-by-id (helpers/org-id-from-url))
      @selected-organisation))

(defn add-all! [orgs]
  (reset! organisations (map #(keywordize-keys %) orgs)))

(defn add-one! [org]
  (swap! organisations conj (keywordize-keys org)))

(defn set-selected! [org]
  (reset! selected-organisation (keywordize-keys org)))

(defn init! []
  (reset! organisations {}))
