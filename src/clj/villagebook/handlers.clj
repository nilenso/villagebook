(ns villagebook.handlers
  (:require [ring.util.response :as res]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [clojure.java.io :as io]))

(defn frontend-handler [request]
  (res/response (io/input-stream (io/resource "public/index.html"))))

(defn api-handler
  [request]
  (if-not (authenticated? request)
    (throw-unauthorized)
    (res/response "Yay! You have access to the API.")))
