(ns villagebookUI.api.auth
  (:require [ajax.core :refer [POST]]))

(def ^:private signup-api "/signup")
(def ^:private login-api "/login")

(defn signup [userdata handler error-handler]
  (POST signup-api
        {:params        userdata
         :format        :raw
         :handler       handler
         :error-handler error-handler}))

(defn login [userdata handler error-handler]
  (POST login-api
        {:params        userdata
         :format        :raw
         :handler       handler
         :error-handler error-handler}))
