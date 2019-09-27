(ns villagebook.field.db
  (:require [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]))

(defn create-fields!
  ([category-id fields]
   (create-fields! (config/db-spec) category-id fields))
  ([conn category-id fields]
   (jdbc/insert-multi! conn :fields (map #(assoc % :category_id category-id) fields))))
