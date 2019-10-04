(ns villagebook.field-value.db
  (:require [clojure.java.jdbc :as jdbc]
            [villagebook.config :as config]))

(defn add-values!
  ([value-rows]
   (add-values! (config/db-spec) value-rows))
  ([conn value-rows]
   (jdbc/insert-multi! conn :field_values value-rows)))
