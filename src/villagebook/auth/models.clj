(ns villagebook.auth.models
  (:require [buddy.hashers :as hasher]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.sign.jwt :as jwt]

            [villagebook.auth.db :as db]
            [villagebook.config :as config]))

(defn create-user
  [userdata]
  (let [email (:email userdata)]
    (if (nil? (db/get-by-email email))
      (let [user (db/create userdata)]
        {:success user})
      {:error "User with this email already exists."})))

(defn get-token
  [email password]
  (let [user  (db/get-by-email email)
        hash  (:password user)
        token (jwt/sign {:user (:email user)} config/jwt-secret)]

    (if user
      (if (hasher/check password hash)
        {:success {:token token}}
        {:error "Invalid password"})
      {:error "Email not found"})))
