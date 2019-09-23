(ns villagebookUI.components.signup
  (:require [reagent.core :as r]
            [villagebookUI.helpers :as helpers :refer [validate-input]]
            [villagebookUI.components.utils :refer [reqd-input]]
            [villagebookUI.api.auth :as auth-api]))

(defn submit-signup
  [user show-validness show-error]
  (helpers/submit-auth-form :signup-form auth-api/signup user show-validness show-error))

(defn signup []
  (let [user      (r/atom {})
        validness (r/atom {})
        error     (r/atom {})
        fields    [{:id :name :type :text :placeholder "Name"}
                   {:id :email :type :email :placeholder "Email"}
                   {:id :password :type :password :placeholder "Password"}
                   {:id :nickname :type :text :placeholder "Choose a nickname"}]]
    (fn []
      [:div.l-page-center.formbox
       [:a.brand {:href "/"} "villagebook"]
       [:form#signup-form.mt-5.form-group
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
                      (submit-signup @user
                                     #(swap! validness assoc :form %)
                                     #(swap! error assoc :message %)))}
         "Signup"]]
       [:span.small "Already have an account? "]
       [:a {:href "/"} "Login"]])))
