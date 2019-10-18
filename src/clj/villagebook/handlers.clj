(ns villagebook.handlers
  (:require [ring.util.response :as res]
            [clojure.java.io :as io]))

(defn frontend-handler [request]
  (res/response (io/input-stream (io/resource "public/index.html"))))
