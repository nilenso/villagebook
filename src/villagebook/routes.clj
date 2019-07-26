(ns villagebook.routes
	(:require [bidi.ring :refer [make-handler]]

            [villagebook.auth.handlers :as auth]
						[villagebook.handlers :refer [api-handler index-handler]]
						[villagebook.middleware :refer [wrap-authz-middleware]]))

;; Setup routes
(def routes
  ["/" {"" index-handler
        "api" (wrap-authz-middleware api-handler)
        "signup" auth/signup
        "login" auth/login}])
