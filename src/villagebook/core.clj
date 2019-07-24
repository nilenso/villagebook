(ns villagebook.core
  (:require [villagebook.migrations :as migrations]
            [villagebook.server :as server]))

;; run migrations via lein run
(defn -main
  [& args]
  (case (first args)
    "migrate"  (migrations/migrate)
    "rollback" (migrations/rollback)))
