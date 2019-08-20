(ns villagebookUI.components.notfound)

(defn notfound []
  [:div.l-page-center.formbox
   [:a.brand {:href "/"} "villagebook"]
   [:h1.notfound.mt-4 "404"]
   [:div.mt-4 "The page you requested doesn't exist."]
   [:a {:href "/"} "Back to home"]])
