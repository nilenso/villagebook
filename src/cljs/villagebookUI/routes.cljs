(ns villagebookUI.routes
  (:require [villagebookUI.fetchers :as fetchers]
            [villagebookUI.middleware :refer [require-login]]
            [villagebookUI.components.login :refer [login]]
            [villagebookUI.components.signup :refer [signup]]
            [villagebookUI.components.dashboard :refer [dashboard]]
            [villagebookUI.components.notfound :refer [notfound]]))

(def routes
  ["" {"/"                login
       "/signup"          signup
       "/dashboard"       (require-login dashboard #(fetchers/fetch-orgs! first))
       ["/orgs/" :org-id] (require-login dashboard #(fetchers/fetch-orgs!))
       true               notfound}])
