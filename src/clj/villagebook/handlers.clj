(ns villagebook.handlers
  (:require [ring.util.response :as res]
            [buddy.auth :refer [authenticated? throw-unauthorized]]))

(defn frontend-handler [request]
  (res/file-response "index.html" {:root "resources/public"}))

(defn api-handler
  [request]
  (if-not (authenticated? request)
    (throw-unauthorized)
    (res/response "Yay! You have access to the API.")))
