(ns villagebook.auth.handlers
  (:require [ring.util.response :as res]
            [buddy.hashers :as hasher]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.sign.jwt :as jwt]

            [villagebook.auth.db :as db]
            [villagebook.auth.models :as models]
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
      (let [user    (models/create-user userdata)
            success (:success user)
            error   (:error user)]
        (if success
          (-> (res/response userdata)
              (res/status 201))
          (res/response error)))
      (res/bad-request "Invalid request."))))

(defn login
  [request]
  (let [{email    :email
         password :password
         :as      userdata} (:params request)]

    (if (auth-spec/valid-login-details? userdata)
      (let [get-token (models/get-token email password)
            token     (get-in get-token [:success :token])
            error     (:error token)]
        (if token
          (res/response {"token" token})
          (-> (res/response error)
              (res/status 401))))
      (res/bad-request "Invalid request."))))

;; TODO: Support token revocation
;; (defn logout
;;   [request]
;;   (res/response "Logged out"))
