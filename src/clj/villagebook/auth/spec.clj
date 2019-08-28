(ns villagebook.auth.spec
  (:require [villagebook.utils :refer [email-pattern required]]
            [villagebook.auth.db :as db]
            [clojure.spec.alpha :as s]))


(defn email?
  [value]
  (and (required value)
       (re-matches email-pattern value)))

(s/def ::email email?)

(s/def ::signup-details (s/keys :req-un [::email ::name ::password ::nickname]))

(defn valid-signup-details?
  [{:keys [email] :as user}]
  (and (s/valid? ::signup-details user) (email? email)))

(s/def ::login-details (s/keys :req-un [::email ::password]))

(defn valid-login-details?
  [{:keys [email password] :as params}]
  (and (s/valid? ::login-details params) (email? email)))
