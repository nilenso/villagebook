(ns villagebook.core
  (:require [ring.adapter.jetty :as jetty]
            [bidi.ring :refer [make-handler]]
            [bidi.bidi :as bidi]
            [ring.util.response :as res]
            [ring.middleware.params :refer [wrap-params]]
            [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

;; Setup routes
(def routes
  ["/" {}])

;; Setup handler with the routes
(def handler
  (make-handler routes))

;; Setup middleware on the handler
(def app-handler
  (-> handler
      wrap-params))

;; For lein run (heroku)
(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty app-handler {:port port :join? false})))
