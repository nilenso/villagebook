(ns villagebookUI.helpers
  (:require [villagebookUI.api.user :as user-api]
            [accountant.core :as accountant]))

(defn random-color []
  (str "hsl(" (->> (.random js/Math)
                   (* 360)
                   (.floor js/Math)) ", 75%, 80%)"))

(defn handle-esc
  [el handler]
  (if (= (.-key el) "Escape")
    (handler)))

(defn html5-valid?
  [element-id]
  (->> element-id
      name
      (.getElementById js/document)
      .checkValidity))

(defn validate-input
  [element-id show-validness]
  (if (html5-valid? element-id)
    (show-validness :valid)
    (show-validness :invalid)))

(defn submit-auth-form
  [form-id api-fn form-data show-validness show-error]
  (validate-input form-id show-validness)
  (if (html5-valid? form-id)
    (api-fn
     form-data
     (fn [res]
       (show-error "")
       (user-api/get-data!)
       (accountant/navigate! "/dashboard"))
     (fn [res]
       (show-error (:response res))))))
