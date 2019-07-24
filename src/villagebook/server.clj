(ns villagebook.server
  (:require [ring.adapter.jetty :as jetty]
            [bidi.ring :refer [make-handler]]
            [bidi.bidi :as bidi]
            [ring.util.response :as res]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-response]]

            [buddy.hashers :as hs]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.backends :as backends]
            [buddy.auth.middleware :refer (wrap-authentication)]
            [buddy.sign.jwt :as jwt]

            [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]]))

(defn find-user
  [username password]
    {:user "Prabhanshu" :id 1})

;; TODO: change to JWE
(def secret "mysecret")
(def auth-backend (backends/jws {:secret secret}))

;; Setup individual controllers
;; TODO: refactor

(defn index-handler
  [request]
  (res/response "Home of villagebook"))

(defn signup-handler
  [request]
  (res/response "Signed up"))

(defn login-handler
  [request]
  (let [data (:params request)
        printed (prn request)
        user (find-user (:username data) ;; (implementation ommited)
                        (:password data))
        token (jwt/sign {:user (:id user)} secret)]
    (res/response {"token" token})))

(defn logout-handler
  [request]
  (res/response "Logged out"))

(defn api-handler
  [request]
  (res/response "Yay! You have access to the API."))

;; Setup routes
(def routes
  ["/" {"" index-handler
        "api" api-handler
        "signup" signup-handler
        "login" login-handler
        "logout" logout-handler}])

;; Setup handler with the routes
(def handler
  (make-handler routes))

;; Setup middleware on the handler
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
