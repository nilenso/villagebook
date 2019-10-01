(ns villagebookUI.routes
  (:require [villagebookUI.fetchers :as fetchers]
            [villagebookUI.middleware :refer [require-login redirect-logged-in]]
            [villagebookUI.components.login :refer [login]]
            [villagebookUI.components.signup :refer [signup]]
            [villagebookUI.components.dashboard :refer [dashboard]]
            [villagebookUI.components.notfound :refer [notfound]]))

(def routes
  ["" {"/"                (redirect-logged-in login "/dashboard")
       "/signup"          (redirect-logged-in signup "/dashboard")
       "/dashboard"       (require-login dashboard #(fetchers/fetch-orgs! first))
       ["/orgs/" :org-id] (require-login dashboard #(fetchers/fetch-orgs!))
       true               notfound}])
