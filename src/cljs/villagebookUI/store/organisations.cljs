(ns villagebookUI.store.organisations
  (:require [reagent.core :as r]
            [clojure.walk :refer [keywordize-keys]]
            [villagebookUI.store.core :as store]))

(def organisations
  (r/cursor store/state [:organisations]))

(def current-organisation
  (r/cursor store/state [:current-organisation]))

(defn get-all []
  @organisations)

(defn get-current []
  @current-organisation)

(defn add-all! [orgs]
  (reset! organisations (into [] (map #(keywordize-keys %) orgs))))

(defn add-one! [org]
  (swap! organisations conj (keywordize-keys org)))

(defn set-current! [org]
  (reset! current-organisation (keywordize-keys org)))

(defn init! []
  (reset! organisations {}))
