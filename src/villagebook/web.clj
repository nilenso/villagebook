(ns villagebook.web
  (:require [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-response]]

            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]

            [bidi.ring :refer [make-handler]]

            [villagebook.routes :refer [routes]]
            [villagebook.config :as config]))

;; Setup handler with the routes
(def handler
  (make-handler routes))

;; Setup all middleware on the handler
(def app-handler
  (-> handler
      (wrap-authentication config/auth-backend)
      (wrap-authorization config/auth-backend)
      wrap-keyword-params
      wrap-params
      wrap-json-response))