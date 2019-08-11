(ns villagebookUI.components.login)

(defn login []
  [:div.center-box
   [:a.brand {:href "/"} "villagebook"]
   [:br]
   [:br]
   [:br]
   [:form
    [:div.form-group
     [:input.form-control {:placeholder "Email"}]
     [:br]
     [:input.form-control {:placeholder "Password" :type "password"}]]
    [:button.btn.btn-outline-primary.login-btn {:type "submit"} "Login"]]
   [:span.small "Don't have an account? "]
   [:a {:href "/signup"} "Signup"]])
