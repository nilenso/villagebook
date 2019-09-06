(ns villagebook.fixtures
  (:require [clojure.java.jdbc :as sql]
            [villagebook.config :as config]
            [villagebook.manage_migrations :as migrations]))

(defn setup-db
  []
  (migrations/migrate))

(defn setup-once
  [test]
  (setup-db)
  (test))

(defn wrap-transaction
  [test]
  (sql/with-db-transaction [trn config/db-spec]
    (sql/db-set-rollback-only! trn)
    (binding [config/db-spec trn]
      (test))))
