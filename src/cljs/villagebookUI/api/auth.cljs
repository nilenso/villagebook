(ns villagebookUI.api.auth
  (:require [ajax.core :refer [POST]]))

(def ^:private signup-api "/signup")
(def ^:private login-api "/login")

(defn signup [userdata handler-fn error-handler-fn]
  (POST signup-api
        {:params userdata
         :format :raw
         :handler handler-fn
         :error-handler error-handler-fn}))

(defn login [userdata handler-fn error-handler-fn]
  (POST login-api
        {:params userdata
         :format :raw
         :handler handler-fn
         :error-handler error-handler-fn}))
