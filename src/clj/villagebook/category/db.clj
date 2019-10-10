(ns villagebook.category.db
  (:require [villagebook.config :as config]
            [clojure.java.jdbc :as jdbc]))

(defn create!
  ([name org-id]
   (create! (config/db-spec) name org-id))
  ([conn name org-id]
   (-> (jdbc/insert! conn :categories {:name name :org_id org-id})
       first)))
