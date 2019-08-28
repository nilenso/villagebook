(ns villagebookUI.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [accountant.core :as accountant]
            [bidi.bidi :as bidi]

            [villagebookUI.routes :refer [routes]]
            [villagebookUI.store :as store]))

(accountant/configure-navigation!
 {:nav-handler (fn [path]
                 (let [matched-route (bidi/match-route routes path)
                       current-page  (:handler matched-route)
                       route-params  (:route-params matched-route)]
                   (session/put! :route {:current-page current-page
                                         :route-params route-params})))
  :path-exists? (fn [path]
                  (boolean (bidi/match-route routes path)))})

(defn root []
  "Root component holding all other components"
  (let [component (:current-page (session/get :route))]
    [component]))

(defn main! []
  (accountant/dispatch-current!)
  (store/init)
  (r/render [root]
            (.getElementById js/document "villagebook-app")))

(defn reload! []
  (main!)
  (prn "Reloaded"))
