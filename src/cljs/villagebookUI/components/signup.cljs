(ns villagebookUI.components.signup)

(defn signup []
  [:div.center-box
   [:a.brand {:href "/"} "villagebook"]
   [:br]
   [:br]
   [:br]
   [:form
    [:div.form-group
     [:input.form-control {:placeholder "Name" :type "text"}]
     [:br]
     [:input.form-control {:placeholder "Email" :type "email"}]
     [:br]
     [:input.form-control {:placeholder "Password" :type "password"}]
     [:button.btn.btn-outline-primary.login-btn {:type "submit"} "Signup"]]]

   [:span.small "Already have an account? "]
   [:a {:href "/login"} "Login"]])
