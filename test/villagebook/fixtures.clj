(ns villagebook.fixtures
  (:require [villagebook.manage_migrations :as migrations]))

(defn setup-test
  []
  (migrations/migrate))

(defn teardown-test
  []
  (migrations/rollback))

(defn wrap-setup
  [fun]
  (setup-test)
  (fun)
  (teardown-test))
