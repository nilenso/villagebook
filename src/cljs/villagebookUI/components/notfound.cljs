(ns villagebookUI.components.notfound)

(defn notfound []
  [:div.center-box
   [:a.brand {:href "/"} "villagebook"]
   [:br]
   [:br]
   [:div "The page you requested doesn't exist."]])
