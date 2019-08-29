(ns villagebook.organisation.db
  (:require [villagebook.config :as config]
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as h]
            [honeysql-postgres.format :refer :all]
            [honeysql-postgres.helpers :as psqlh]))

(defn get-by-id [id]
  (-> (jdbc/query (config/db-spec) (-> (h/select :*)
                                     (h/from :organisations)
                                     (h/where [:= :id id])
                                     (sql/format)))
      first))

(defn create! [{:keys [name color]}]
  (-> (jdbc/insert! (config/db-spec) :organisations {:name name :color color})
      first))

(defn add-user-as!
  "Adds a user to the organisation with a permission"
  [org-id user-id permission]
  (jdbc/db-do-prepared-return-keys (config/db-spec)
                                   (-> (h/insert-into :organisation_permissions)
                                       (h/values [{:org_id     org-id
                                                   :user_id    user-id
                                                   :permission (sql/inline
                                                                (str "'"permission"'::permission"))}])
                                     (psqlh/returning :*)
                                     (sql/format))))
