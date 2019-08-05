(ns villagebook.routes
  (:require [bidi.ring :refer [make-handler]]
            [buddy.auth.middleware :refer [wrap-authorization]]

            [villagebook.middleware :refer [with-auth]]
            [villagebook.auth.handlers :as auth]
            [villagebook.organisation.handlers :as org]
            [villagebook.handlers :refer [api-handler index-handler]]

            [villagebook.config :as config]))

(def apiroutes
  {"organisation" {["/" :id] {:get (with-auth org/get-by-id)}
                   :post (with-auth org/create-organisation)}})

;; Setup routes
(def routes
  ["/" {""       index-handler
        "api/"   apiroutes
        "signup" auth/signup
        "login"  auth/login}])
