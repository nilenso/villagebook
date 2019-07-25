(ns villagebook.core
  (:use     [ring.adapter.jetty])
  (:require [villagebook.migrations :as migrations]
            [villagebook.server :as server]))

;; run migrations via lein run migrate
(defn -main
  [& args]
  (if (count args)
    (case (first args)
      "migrate"  (migrations/migrate)
      "rollback" (migrations/rollback))
    (run-jetty server/app-handler {:port 3000})))

(defonce server (atom nil))

(defn start!
  []
  (run-jetty server/app-handler {:port 3000}))

(defn stop!
  []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))
