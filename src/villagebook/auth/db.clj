(ns villagebook.auth.db
	(:require	[clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as honey]
            [buddy.hashers :as hasher]

            [villagebook.config :as config]))

(defn get-user-by-email
  "Fetches user by email. Returns nil if not found."
  [email]
  (-> (jdbc/query config/db-spec (-> (honey/select :*)
                                     (honey/from :users)
                                     (honey/where [:= :email email])
                                     sql/format))
      first))

(defn create-user
  "Creates a user with given fields."
  [userdata]
  (let [hashed-pass (hasher/derive (:password userdata))]
    (jdbc/execute! config/db-spec (->  (honey/insert-into :users)
                                       (honey/values [{:nickname (:nickname userdata)
                                                       :email    (:email userdata)
                                                       :password hashed-pass
                                                       :name     (:name userdata)}])
                                       sql/format))))
