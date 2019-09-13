(ns villagebookUI.helpers)

(defn random-color []
  (str "hsl(" (->> (.random js/Math)
                   (* 360)
                   (.floor js/Math)) ", 75%, 80%)"))

(defn handle-esc
  [el handler]
  (if (= (.-key el) "Escape")
    (handler)))
