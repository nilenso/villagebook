(ns villagebook.config
  (:require [aero.core :refer (read-config)]
            [clojure.java.io :as io]))

(def config (atom nil))

(defn get-config-profile
  []
  (keyword (System/getenv "CONFIG_PROFILE")))

(defn init
  [& profile]
  (reset! config (read-config (io/resource "config.edn") {:profile (or (get-config-profile) (first profile))})))

(defn db-spec
  []
  (:db-spec @config))

(defn jwt-secret
  []
  (:jwt-secret @config))

(defn auth-config
  []
  {:secret (jwt-secret)})
