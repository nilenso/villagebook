(ns villagebook.category.db
  (:require [villagebook.config :as config]
            [clojure.java.jdbc :as jdbc]))

(defn create!
  [name org-id]
  (-> (jdbc/insert! (config/db-spec) :categories {:name name :org_id org-id})
      first))
