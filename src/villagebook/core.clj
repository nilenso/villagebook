(ns villagebook.core
  (:use     [ring.adapter.jetty])
  (:require [villagebook.manage_migrations :as migrations]
            [villagebook.web :as web]
            [environ.core :refer [env]]
            [villagebook.config :as config]))

;; run migrations via lein run migrate
(defn -main
  [& args]
  (if (count args)
    (case (first args)
      "migrate"  (migrations/migrate)
      "rollback" (migrations/rollback))
    (run-jetty web/app-handler {:port 3000})))

;; (defonce server (atom nil))

;; (defn start!
;;   []
;;   (run-jetty server/app-handler {:port 3000}))

;; (defn stop!
;;   []
;;   (when-not (nil? @server)
;;     (@server :timeout 100)
;;     (reset! server nil)))
