(ns villagebookUI.helpers
  (:require [villagebookUI.store.session :as session]
            [villagebookUI.store.ui :as ui-store]))

(defn random-color []
  (str "hsl(" (->> (.random js/Math)
                   (* 360)
                   (.floor js/Math)) ", 75%, 80%)"))

(defn handle-esc
  [el handler]
  (if (= (.-key el) "Escape")
    (handler)))

(defn org-id-from-url []
  (-> (session/route-params)
      :org-id
      js/parseInt))

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
  [form-id api-fn form-data on-success show-validness show-error]
  (validate-input form-id show-validness)
  (if (html5-valid? form-id)
    (api-fn
     form-data
     (fn [res]
       (show-error "")
       (on-success))
     (fn [res]
       (show-error (:response res))))))

(defn show-alert-bottom
  [status message & [time]]
  (let [time (if (number? time) (* time 1000) 4000)]
    (ui-store/set! :alert-bottom {:status  status
                                  :message message})
    (js/setTimeout #(ui-store/set! :alert-bottom nil) time)))
