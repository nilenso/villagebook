(ns villagebook.field-value.db
  (:require [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]))

(defn- vals-by-keys
  [m ks]
  (reduce #(conj %1 (%2 m)) [] ks))

(defn add-values!
  ([value-rows]
   (add-values! (config/db-spec) value-rows))
  ([conn value-rows]
   (let [columns (keys (first value-rows))]
     (jdbc/insert-multi! conn :field_values
                         columns
                         (map #(vals-by-keys % columns) value-rows)))))

(defn retrieve-by-item
  [item-id]
  (jdbc/find-by-keys (config/db-spec) :field_values {:item_id item-id}))
