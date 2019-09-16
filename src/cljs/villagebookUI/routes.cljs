(ns villagebookUI.routes
  (:require [reagent.session :as session]
            [villagebookUI.middleware :refer [require-login redirect-logged-in]]
            [villagebookUI.components.login :refer [login]]
            [villagebookUI.components.signup :refer [signup]]
            [villagebookUI.components.dashboard :refer [dashboard]]
            [villagebookUI.components.notfound :refer [notfound]]))

(def routes
  ["" {"/"          (redirect-logged-in login "/dashboard")
       "/signup"    (redirect-logged-in signup "/dashboard")
       "/dashboard" (require-login dashboard)
       true         notfound}])
