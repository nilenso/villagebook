(ns villagebook.organisation.db
  (:require [villagebook.config :as config]
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as h]
            [honeysql-postgres.format :refer :all]
            [honeysql-postgres.helpers :as psqlh]))

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

(defn retrieve
  ([]
   "Returns a list of all organisations"
    (jdbc/query (config/db-spec) (-> (h/select :*)
                                     (h/from :organisations)
                                     (sql/format))))
  ([id]
   "Returns an organisation by id"
   (jdbc/get-by-id (config/db-spec) :organisations id)))

(defn retrieve-by-user
  ([user-id]
   "Returns a list of organisations belonging to a user with permission on each."
   (jdbc/query (config/db-spec) (-> (h/select :*)
                                    (h/from :organisations)
                                    (h/join :organisation_permissions
                                            [:= :organisations.id :organisation_permissions.org_id])
                                    (h/where [:= :user-id user-id])
                                    (sql/format)))))
