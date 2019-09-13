(ns villagebook.user.db
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as honey]
            [buddy.hashers :as hasher]

            [villagebook.config :as config]))


(defn create!
  "Creates a user with given fields."
  ([userdata]
   (create! (config/db-spec) userdata))
  ([db-spec userdata]
   (let [hashed-pass (hasher/derive (:password userdata))]
     (-> (jdbc/insert! db-spec :users {:nickname (:nickname userdata)
                                       :email    (:email userdata)
                                       :password hashed-pass
                                       :name     (:name userdata)})
         first))))

(defn retrieve
  "Fetches user by id. Return nil if not found."
  [id]
  (jdbc/get-by-id (config/db-spec) :users id))

(defn retrieve-by-email
  "Fetches user by email. Returns nil if not found."
  ([email]
   (retrieve-by-email (config/db-spec) email))
  ([db-spec email]
   (-> (jdbc/query (config/db-spec) (-> (honey/select :*)
                                        (honey/from :users)
                                        (honey/where [:= :email email])
                                        sql/format))
       first)))
