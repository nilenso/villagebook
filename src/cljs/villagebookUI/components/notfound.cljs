(ns villagebookUI.components.notfound)

(defn notfound []
  [:div.center-box
   [:a.brand {:href "/"} "villagebook"]
   [:div.mt-4 "The page you requested doesn't exist."]
   [:a {:href "/"} "Back to home"]])
