(ns villagebook.organisation.spec
  (:require [villagebook.utils :refer [required]]
            [clojure.spec.alpha :as s]))

(s/def ::id (s/and int? #(> % 0)))
(s/def ::organisation-details (s/keys :req-un [::name]))
(def permissions #{:owner :member :none})

(defn valid-organisation-details?
  [organisation]
  (s/valid? ::organisation-details organisation))

(defn valid-permission?
  [permission]
  (if (s/valid? permissions permission)
    permission
    :none))
