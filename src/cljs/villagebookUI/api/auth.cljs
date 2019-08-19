(ns villagebookUI.api.auth
  (:require [ajax.core :refer [POST]]))

(def ^:private signup-api "/signup")

(defn signup [userdata handler-fn error-handler-fn]
  (POST signup-api
        {:params userdata
         :format :raw
         :handler handler-fn
         :error-handler error-handler-fn}))
