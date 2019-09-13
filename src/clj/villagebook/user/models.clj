(ns villagebook.user.models
  (:require [buddy.hashers :as hasher]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.sign.jwt :as jwt]

            [villagebook.user.db :as db]
            [villagebook.config :as config]
            [clojure.java.jdbc :as jdbc]))

(defn create!
  "Creates a new user if a user with same email doesn't exist already."
  [{:keys [email] :as userdata}]
  (jdbc/with-db-transaction [trn (config/db-spec)]
    (if (db/retrieve-by-email trn email)
      {:error "User with this email already exists."}
      (let [user (db/create! trn userdata)]
        {:success user}))))

(defn get-token
  [email password]
  (let [{hashed-password :password
         db-email        :email
         id              :id} (db/retrieve-by-email email)
        token    (jwt/sign {:email db-email :id id} (config/jwt-secret))]
    (if id
      (if (hasher/check password hashed-password)
        {:success {:token token}}
        {:error "Invalid password"})
      {:error "Email not found"})))

(defn retrieve
  [id]
  (dissoc (db/retrieve id) :password :created_at :id))

(defn retrieve-by-email
  [email]
  (dissoc (db/retrieve-by-email email) :password :created_at :id))
