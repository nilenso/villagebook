(ns villagebook.item.db
  (:require [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]
            [honeysql.core :as sql]
            [honeysql.helpers :as h]))

(defn create!
  ([category-id]
   (create! (config/db-spec) category-id))
  ([conn category-id]
   (-> (jdbc/insert! conn :items {:category_id category-id})
       first)))
