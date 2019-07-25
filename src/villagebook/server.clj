(ns villagebook.server
  (:require [ring.adapter.jetty :as jetty]
            [bidi.ring :refer [make-handler]]
            [bidi.bidi :as bidi]
            [ring.util.response :as res]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-response]]

            [buddy.hashers :as hasher]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.backends :as backends]
            [buddy.auth.middleware :refer (wrap-authentication)]
            [buddy.sign.jwt :as jwt]

            [villagebook.config :as config]
            [environ.core :refer [env]]

            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as honey]))

(defn find-user
  [email password]
    {:user "Prabhanshu" :id 1})

;; TODO: change to JWE
(def secret "mysecret")
(def auth-backend (backends/jws {:secret secret}))

;; Setup individual controllers
;; TODO: refactor
(defn index-handler
  [request]
  (res/response "Home of villagebook"))

(defn get-user-by-email
  [email]
  (-> (jdbc/query config/db-spec (-> (honey/select :*)
                                     (honey/from :users)
                                     (honey/where [:= :email email])
                                     sql/format))
      first))

(defn signup-handler
  [request]
  (let [{nickname :nickname
         email    :email
         password :password
         name     :name
         :as      userdata} (-> request :params)
        hashed-pass   (hasher/derive password)]

    ;; Insert in DB
    (if (nil? (get-user-by-email email))
      (do
        (jdbc/execute! config/db-spec (->  (honey/insert-into :users)
                                           (honey/values [{:nickname nickname
                                                           :email     email
                                                           :password hashed-pass
                                                           :name     name}])
                                           sql/format))
         (res/response "Signed up"))
        (res/response "User already exists."))))

(defn login-handler
  [request]
  (let [data (:params request)
        user (find-user (:email data) ;; (implementation ommited)
                            (:password data))
        token (jwt/sign {:user (:id user)} secret)]
    (res/response {"token" token})))

(defn logout-handler
  [request]
  (res/response "Logged out"))

(defn api-handler
  [request]
  (res/response "Yay! You have access to the API."))

;; Setup middleware
;; TODO: refactor
(defn wrap-authz-middleware [handler]
  (fn [req]
    (handler req)))

;; Setup routes
(def routes
  ["/" {"" index-handler
        "api" (wrap-authz-middleware api-handler)
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
