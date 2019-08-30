(ns villagebookUI.components.login
  (:require [reagent.core :as r]
            [accountant.core :as accountant]

            [villagebookUI.api.auth :as auth]
            [villagebookUI.store.user :as user-store]))

(defn validate!
  [elementID formdata]
  (if (.checkValidity (.getElementById js/document elementID))
    (swap! formdata assoc-in [:validation-classes elementID] "")
    (swap! formdata assoc-in [:validation-classes elementID] "check-invalid")))

(defn input [formdata k placeholder type & required]
  [:input.form-control.mt-3.mt-3 {:placeholder placeholder
                                  :name        k
                                  :id          k
                                  :on-change   #(swap! formdata assoc-in [:user k] (-> % .-target .-value))
                                  :on-blur     #(validate! (name k) formdata)
                                  :type        type
                                  :required    required
                                  :class       (str
                                                (get-in @formdata [:validation-classes (name k)]) " "
                                                (get-in @formdata [:validation-classes "login-form"]))}])

(defn login []
  (let [formdata (r/atom {})
        error    (r/atom {})]
    (fn []
      (if (user-store/get)
        (do
          (accountant/navigate! "/dashboard")
          [:div])
        [:div.l-page-center.formbox
         [:a.brand {:href "/"} "villagebook"]
         [:form#login-form.mt-5.form-group
          [input formdata :email "Email" "email" :required]
          [input formdata :password "Password" "password" :required]
          [:div.auth-error (:message @error)]
          [:button.btn.btn-outline-primary.login-btn.mt-2
           {:type    "submit"
            :on-click #(do
                         (.preventDefault %)
                         (validate! "login-form" formdata)
                         (auth/login
                          (:user @formdata)
                          (fn [res]
                            (swap! error assoc :message "")
                            (user-store/fetching!)
                            (accountant/navigate! "/dashboard"))
                          (fn [res]
                            (swap! error assoc :message (:response res)))))}
           "Login"]]
         [:span.small "Don't have an account? "]
         [:a {:href "/signup"} "Signup"]]))))
