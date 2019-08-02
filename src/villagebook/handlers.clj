(ns villagebook.handlers
  (:require [ring.util.response :as res]
            [buddy.auth :refer [authenticated? throw-unauthorized]]))

;;TODO move to different handler dirs
(defn index-handler
  [request]
  (res/response "Home of villagebook"))

(defn api-handler
  [request]
  (if-not (authenticated? request)
    (throw-unauthorized)
    (res/response "Yay! You have access to the API.")))
