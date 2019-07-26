(ns villagebook.auth.handlers
	(:require [ring.util.response :as res]

						[buddy.hashers :as hasher]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.sign.jwt :as jwt]

            [villagebook.auth.db :as db]
            [villagebook.config :as config]

            ;;TODO move this to db.clj
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as honey]))

(defn signup
  [request]
  (let [{nickname :nickname
         email    :email
         password :password
         name     :name
         :as      userdata} (-> request :params)
        hashed-pass   (hasher/derive password)]

    ;; Insert in DB
    (if (nil? (db/get-user-by-email email))
      (do
        (jdbc/execute! config/db-spec (->  (honey/insert-into :users)
                                           (honey/values [{:nickname nickname
                                                           :email     email
                                                           :password hashed-pass
                                                           :name     name}])
                                           sql/format))
         (res/response "Signed up"))
        (res/response "User already exists."))))

(defn login
  [request]
  (let [data (:params request)
        user (db/find-user (:email data) ;; (implementation ommited)
                        (:password data))
        token (jwt/sign {:user (:id user)} config/jwt-secret)]
    (res/response {"token" token})))

(defn logout
  [request]
  (res/response "Logged out"))

(defn api
  [request]
  (res/response "Yay! You have access to the API."))