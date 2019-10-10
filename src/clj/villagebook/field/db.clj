(ns villagebook.field.db
  (:require [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]))

(defn create-fields!
  ([fields]
   (create-fields! (config/db-spec) fields))
  ([conn fields]
   (jdbc/insert-multi! conn :fields fields)))
