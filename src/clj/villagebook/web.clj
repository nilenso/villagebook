(ns villagebook.web
  (:require [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.cookies :refer [wrap-cookies]]

            [ring.util.response :as res]
            [ring.util.request :as req]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]

            [bidi.ring :refer [make-handler]]
            [bidi.bidi :as bidi]

            [villagebook.middleware :refer [ignore-trailing-slash]]
            [villagebook.routes :refer [routes]]
            [villagebook.config :as config]))

;; Setup handler with the routes
(def handler
  (make-handler routes))

;; Setup all middleware on the handler
(defn app-handler
  []
  (-> handler
      (wrap-authentication (config/auth-backend))
      (wrap-authorization (config/auth-backend))
      wrap-cookies
      ignore-trailing-slash
      wrap-keyword-params
      wrap-params
      wrap-json-response))

(def dev-handler (app-handler))
