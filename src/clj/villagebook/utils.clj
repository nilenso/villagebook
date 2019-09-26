(ns villagebook.utils
  (:require [ring.util.response :as res]))

(def email-pattern #"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")

(def alpha-num-pattern #"^[a-zA-Z0-9]+$")

(defn required
  [value]
  (and (string? value)
       (not-empty value)))

(defn model-to-http
  "Translates a model's response to an http response with given status code."
  [message response-codes]
  (let [status (-> message keys first)
        result (get message status)]
    (-> (res/response result)
        (res/status (get response-codes status)))))
