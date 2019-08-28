(ns villagebook.user.models
  (:require [buddy.hashers :as hasher]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.sign.jwt :as jwt]

            [villagebook.user.db :as db]
            [villagebook.config :as config]))

(defn create-user
  [{:keys [email] :as userdata}]
  (if-not (db/get-by-email email)
    (let [user (db/create userdata)]
      {:success user})
    {:error "User with this email already exists."}))

(defn get-token
  [email password]
  (let [user  (db/get-by-email email)
        {hashed-password :password db-email :email} user
        token (jwt/sign {:user db-email} (config/jwt-secret))]

    (if user
      (if (hasher/check password hashed-password)
        {:success {:token token}}
        {:error "Invalid password"})
      {:error "Email not found"})))

(defn get-by-email
  [email]
  (dissoc (db/get-by-email email) :password :created_at :id))
