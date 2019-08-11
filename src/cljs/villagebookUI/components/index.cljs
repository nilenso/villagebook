(ns villagebookUI.components.index
  (:require [reagent.session :as session]
            [bidi.bidi :as bidi]))

(defn index []
  ;;(Add landing page here)
  [:div.center-box
   [:a.brand {:href "/"} "villagebook"]
   [:br]
   [:br]
   [:a.btn.btn-outline-primary.login-btn {:href "/login"} "Login"]
   [:a.btn.btn-outline-primary.login-btn {:href "/signup"} "Signup"]])
