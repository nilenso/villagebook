(ns villagebook.config)

(def db-spec "jdbc:postgresql://localhost:5432/villagebook")

;; TODO: change jwt to jwe
(def jwt-secret "mysecret")