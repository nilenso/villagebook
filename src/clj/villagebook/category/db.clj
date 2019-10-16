(ns villagebook.category.db
  (:require [villagebook.config :as config]
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as h]))

(defn create!
  ([name org-id]
   (create! (config/db-spec) name org-id))
  ([conn name org-id]
   (-> (jdbc/insert! conn :categories {:name name :org_id org-id})
       first)))

(defn retrieve
  ([id]
   (retrieve (config/db-spec) id))
  ([conn id]
   (-> (jdbc/find-by-keys conn :categories {:id id})
       first)))

(defn retrieve-by-org
  [org-id]
  (jdbc/find-by-keys (config/db-spec) :categories {:org_id org-id}))
