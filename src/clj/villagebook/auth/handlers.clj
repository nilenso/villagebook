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
  [{userdata :params :as request}]
  (if (auth-spec/valid-signup-details? userdata)
    (let [message       (models/create-user userdata)
          email         (get-in message [:success :email])
          error         (:error message)
          password      (:password userdata)
          token-message (models/get-token email password)
          token         (get-in token-message [:success :token])]
      (if email
        (-> (res/response {:email email :token token})
            (res/status 201))
        (res/response error)))
    (res/bad-request "Invalid request.")))

(defn login
  [{userdata :params :as request}]
  (if (auth-spec/valid-login-details? userdata)
    (let [{:keys [email password]} userdata
          message (models/get-token email password)
          token   (get-in message [:success :token])
          error   (:error message)]
      (if token
        (res/response {:token token})
        (-> (res/response error)
            (res/status 401))))
    (res/bad-request "Invalid request.")))

;; TODO: Support token revocation
;; (defn logout
;;   [request]
;;   (res/response "Logged out"))
