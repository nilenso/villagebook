(ns villagebookUI.routes
  (:require [reagent.session :as session]
            [villagebookUI.utils :refer [protected]]
            [villagebookUI.components.login :refer [login]]
            [villagebookUI.components.signup :refer [signup]]
            [villagebookUI.components.dashboard :refer [dashboard]]
            [villagebookUI.components.notfound :refer [notfound]]))

(def routes
  ["" {"/"                login
       "/signup"          signup
       "/dashboard"       (protected dashboard)
       ["/orgs/" :org-id] (protected dashboard)
       true               notfound}])
