(ns villagebook.routes
  (:require [bidi.ring :refer [make-handler resources]]
            [buddy.auth.middleware :refer [wrap-authorization]]

            [villagebook.middleware :refer [with-auth]]
            [villagebook.auth.handlers :as auth]
            [villagebook.organisation.handlers :as org]
            [villagebook.handlers :refer [api-handler frontend-handler]]
            [villagebook.config :as config]))

(def apiroutes
  {"organisations" {["/" :id] {:get (with-auth org/get-by-id)}
                    :post     (with-auth org/create-organisation)}})

;; Setup routes
(def routes
  ["/" {"assets" (resources {:prefix "public/assets/"})
        "api/"   apiroutes
        "signup" {:post auth/signup}
        "login"  {:post auth/login}
        true     frontend-handler}])
