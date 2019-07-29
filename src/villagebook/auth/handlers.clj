(ns villagebook.auth.handlers
  (:require [ring.util.response :as res]
            [buddy.hashers :as hasher]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.sign.jwt :as jwt]

            [villagebook.auth.db :as db]
            [villagebook.config :as config]
            [villagebook.auth.spec :as auth-spec]))

(defn signup
  [request]
  (let [{nickname :nickname
         email    :email
         password :password
         name     :name
         :as      userdata} (:params request)]

    (if (auth-spec/valid-signup-details? userdata)
      (if (nil? (db/get-user-by-email email))
        (do
          (db/create-user userdata)
          (-> (res/response userdata)
              (res/status 201)))
        (res/response "User already exists."))
      (res/bad-request "Invalid request."))))

(defn login
  [request]
  (let [{email    :email
         password :password
         :as      userdata} (:params request)
        user  (db/get-user-by-email email)
        hash  (:password user)
        token (jwt/sign {:user (:email user)} config/jwt-secret)]

    (if (auth-spec/valid-login-details? userdata)
      (if (nil? user)
        (->  (res/response "Email not found.")
             (res/status 401))
        (if (hasher/check password hash)
            (res/response {"token" token})
            (-> (res/response "Invalid password.")
                (res/status 401))))
      (res/bad-request "Invalid request."))))

;; TODO: Support token revocation
;; (defn logout
;;   [request]
;;   (res/response "Logged out"))
