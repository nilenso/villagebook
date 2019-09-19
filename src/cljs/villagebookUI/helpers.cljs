(ns villagebookUI.helpers
  (:require [villagebookUI.store.session :as session]))

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
