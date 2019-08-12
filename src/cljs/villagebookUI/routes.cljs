(ns villagebookUI.routes
  (:require [reagent.session :as session]
            [villagebookUI.components.index :refer [index]]
            [villagebookUI.components.login :refer [login]]
            [villagebookUI.components.signup :refer [signup]]
            [villagebookUI.components.notfound :refer [notfound]]))

(def routes
  ["" {"/"       index
       "/login"  login
       "/signup" signup
       true      notfound}])
