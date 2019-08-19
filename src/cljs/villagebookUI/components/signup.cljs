(ns villagebookUI.components.signup
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [accountant.core :as accountant]

            [villagebookUI.api.auth :as auth]
            [villagebookUI.store :as store]))

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
                                                (get-in @formdata [:validation-classes "signup-form"]))}])

(defn signup []
  (let [formdata (r/atom {})
        error    (r/atom {})]
    (fn []
      [:div.center-box
       [:a.brand {:href "/"} "villagebook"]
       [:form#signup-form.mt-5.form-group
        [input formdata :name "Name" "text" :required]
        [input formdata :email "Email" "email" :required]
        [input formdata :password "Password" "password" :required]
        [input formdata :nickname "Nickname" "text" :required]
        [:div.auth-error (:message @error)]
        [:button.btn.btn-outline-primary.login-btn.mt-4
         {:type     "submit"
          :on-click #(do
                       (.preventDefault %)
                       (validate! "signup-form" formdata)
                       (auth/signup
                        (:user @formdata)
                        (fn [res]
                          (swap! error assoc :message "")
                          (swap! store/state assoc :user res)
                          #_(accountant/navigate! "/"))
                        (fn [res]
                          (swap! error assoc :message (:response res)))))}
         "Signup"]]
       [:span.small "Already have an account? "]
       [:a {:href "/login"} "Login"]])))
