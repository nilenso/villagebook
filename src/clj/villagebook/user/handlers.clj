(ns villagebook.user.handlers
  (:require [ring.util.response :as res]
            [buddy.hashers :as hasher]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.sign.jwt :as jwt]

            [villagebook.user.db :as db]
            [villagebook.user.models :as models]
            [villagebook.config :as config]
            [villagebook.user.spec :as user-spec]))

(defn signup
  [{userdata :params :as request}]
  (if (user-spec/valid-signup-details? userdata)
    (let [message       (models/create-user userdata)
          email         (get-in message [:success :email])
          error         (:error message)
          password      (:password userdata)
          token-message (models/get-token email password)
          token         (get-in token-message [:success :token])]
      (if email
        (-> (res/response {:email email :token token})
            (res/set-cookie "token" token {:http-only true})
            (res/status 201))
        (-> (res/response error)
            (res/status 403))))
    (res/bad-request "Invalid request.")))

(defn login
  [{userdata :params :as request}]
  (if (user-spec/valid-login-details? userdata)
    (let [{:keys [email password]} userdata
          message (models/get-token email password)
          token   (get-in message [:success :token])
          error   (:error message)]
      (if token
        (-> (res/response {:token token})
            (res/set-cookie "token" token {:http-only true}))
        (-> (res/response error)
            (res/status 401))))
    (res/bad-request "Invalid request.")))

(defn retrieve
  [{identity :identity :as request}]
  (if-let [user (:email identity)]
    (if-let [userdata (models/get-by-email user)]
      (res/response userdata)
      (-> (res/response "Something went wrong.")
          (res/status 500)))
    (res/bad-request "Invalid request.")))

;; TODO: Support token revocation
;; (defn logout
;;   [request]
;;   (res/response "Logged out"))
