(ns villagebook.organisation.db
  (:require [villagebook.config :as config]
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as h]))

(defn get-by-id [id]
  (-> (jdbc/query config/db-spec (-> (h/select :*)
                                     (h/from :organisations)
                                     (h/where [:= :id id])
                                     (sql/format)))
      first))

(defn create [{:keys [name color]}]
  (-> (jdbc/insert! config/db-spec :organisations {:name name :color color})
      first))
