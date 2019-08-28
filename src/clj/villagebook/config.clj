(ns villagebook.config
  (:require [villagebook.auth.backend :as backend]))

(def db-spec "jdbc:postgresql://localhost:5432/villagebook?user=postgres")

;; TODO: change jwt to jwe
(def jwt-secret "mysecret")

;; Setup auth middleware
(def auth-backend (backend/custom-backend {:secret jwt-secret}))
