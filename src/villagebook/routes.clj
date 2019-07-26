(ns villagebook.routes
	(:require [bidi.ring :refer [make-handler]]
            [buddy.auth.middleware :refer [wrap-authorization]]

            [villagebook.auth.handlers :as auth]
						[villagebook.handlers :refer [api-handler index-handler]]
            [villagebook.config :as config]))

;; Setup routes
(def routes
  ["/" {"" index-handler
        "api" (wrap-authorization api-handler config/auth-backend)
        "signup" auth/signup
        "login" auth/login}])
