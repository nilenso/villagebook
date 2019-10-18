(ns villagebook.utils
  (:require [ring.util.response :as res]
            [clojure.edn :as edn]))

(defn required
  [value]
  (every-pred string? not-empty))

(defn model-to-http
  "Translates a model's response to an http response with given status code."
  [{:keys [message response-codes]}]
  (let [status (-> message keys first)
        result (get message status)]
    (-> (res/response result)
        (res/status (get response-codes status)))))

(defn string->int
  [param]
  (edn/read-string param))
