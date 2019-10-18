(ns villagebook.user.spec
  (:require [villagebook.utils :refer [required]]
            [villagebook.user.db :as db]
            [clojure.spec.alpha :as s]))

(def email-pattern #"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")

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
