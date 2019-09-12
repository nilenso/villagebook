(ns villagebookUI.routes
  (:require [reagent.session :as session]
            [villagebookUI.middleware :refer [require-login]]
            [villagebookUI.components.login :refer [login]]
            [villagebookUI.components.signup :refer [signup]]
            [villagebookUI.components.dashboard :refer [dashboard]]
            [villagebookUI.components.notfound :refer [notfound]]))

(def routes
  ["" {"/"          login
       "/signup"    signup
       "/dashboard" (require-login dashboard)
       true         notfound}])
