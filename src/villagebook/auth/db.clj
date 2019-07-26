(ns villagebook.auth.db
	(:require	[clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as honey]

            [villagebook.config :as config]))

(defn find-user
  [email password]
    {:user "Prabhanshu" :id 1})

(defn get-user-by-email
  [email]
  (-> (jdbc/query config/db-spec (-> (honey/select :*)
                                     (honey/from :users)
                                     (honey/where [:= :email email])
                                     sql/format))
      first))

(def create-user
	[])