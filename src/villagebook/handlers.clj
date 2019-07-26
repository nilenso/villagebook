(ns villagebook.handlers
	(:require [ring.util.response :as res]))

;;TODO move to different handler dirs
(defn index-handler
  [request]
  (res/response "Home of villagebook"))

(defn api-handler
  [request]
  (res/response "Yay! You have access to the API."))
