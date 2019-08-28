(ns villagebook.config
  (:require [buddy.auth.backends :as backends]))

(def db-spec "jdbc:postgresql://localhost:5432/villagebook?user=postgres")

;; TODO: change jwt to jwe
(def jwt-secret "mysecret")

;; Setup auth middleware
(def auth-backend (backends/jws {:secret jwt-secret}))
