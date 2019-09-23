(ns villagebookUI.components.login
  (:require [reagent.core :as r]
            [villagebookUI.helpers :as helpers :refer [validate-input]]
            [villagebookUI.components.utils :refer [reqd-input]]
            [villagebookUI.api.auth :as auth-api]))

(defn submit-login
  [user show-validness show-error]
  (helpers/submit-auth-form :login-form auth-api/login user show-validness show-error))

(defn login []
  (let [user      (r/atom {})
        validness (r/atom {})
        error     (r/atom {})
        fields    [{:id :email :type :email :placeholder "Email"}
                   {:id :password :type :password :placeholder "Password"}]]
    (fn []
      [:div.l-page-center.formbox
       [:a.brand {:href "/"} "villagebook"]
       [:form#login-form.mt-5.form-group
        (doall
         (for [field fields
               :let  [id (:id field)]]
           [reqd-input {:id          id
                        :key         id
                        :type        (:type field)
                        :placeholder (:placeholder field)
                        :class       [:form-control :mt-3 (get @validness id)(:form @validness)]
                        :on-change   #(swap! user assoc id %)
                        :on-blur     #(validate-input id
                                                      (fn [v] (swap! validness assoc id v)))}]))
        [:div.auth-error (:message @error)]
        [:button.btn.btn-outline-primary.login-btn.mt-2
         {:type     "submit"
          :on-click (fn [e]
                      (.preventDefault e)
                      (submit-login @user
                                    #(swap! validness assoc :form %)
                                    #(swap! error assoc :message %)))}
         "Login"]]
       [:span.small "Don't have an account? "]
       [:a {:href "/signup"} "Signup"]])))
