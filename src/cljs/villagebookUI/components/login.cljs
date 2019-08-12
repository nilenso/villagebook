(ns villagebookUI.components.login)

(defn login []
  [:div.center-box
   [:a.brand {:href "/"} "villagebook"]
   [:br]
   [:br]
   [:br]
   [:form
    [:div.form-group
     [:input.form-control {:placeholder "Email" :required true}]
     [:br]
     [:input.form-control {:placeholder "Password" :type "password" :required true}]
     [:button.btn.btn-outline-primary.login-btn {:type "submit"} "Login"]]]
   [:span.small "Don't have an account? "]
   [:a {:href "/signup"} "Signup"]])
