(ns villagebook.web
  (:require [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-response]]
            
            [buddy.auth.middleware :refer [wrap-authentication]]
            [buddy.auth.backends :as backends]

            [bidi.ring :refer [make-handler]]

            [villagebook.routes :refer [routes]]
            [villagebook.config :as config]))

;; Setup handler with the routes
(def handler
  (make-handler routes))

;; Setup auth middleware
(def auth-backend (backends/jws {:secret config/jwt-secret}))

;; Setup all middleware on the handler
(def app-handler
  (-> handler
      (wrap-authentication auth-backend)
      wrap-keyword-params
      wrap-params
      wrap-json-response))

;; For lein run (heroku)
;; (defn -main [& [port]]
;;   (let [port (Integer. (or port (env :port) 5000))]
;;     (jetty/run-jetty app-handler {:port port :join? false})))