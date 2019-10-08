(ns villagebook.field.db
  (:require [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]
            [villagebook.config :as config]
            [honeysql.helpers :as h]
            [honeysql.core :as sql]))

(defn create-fields!
  ([fields]
   (create-fields! (config/db-spec) fields))
  ([conn fields]
   (jdbc/insert-multi! conn :fields fields)))

(defn retrieve-by-org
  [org-id]
  (jdbc/query (config/db-spec) (-> (h/select :fields.*)
                                   (h/from :fields)
                                   (h/join :categories
                                           [:= :categories.id :fields.category_id])
                                   (h/where [:= :org_id org-id])
                                   (sql/format))))
