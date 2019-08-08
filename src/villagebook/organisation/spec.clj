(ns villagebook.organisation.spec
  (:require [villagebook.utils :refer [required]]
            [clojure.spec.alpha :as s]))

(s/def ::organisation-details (s/keys :req-un [::name]))

(defn valid-organisation-details?
  [organisation]
  (s/valid? ::organisation-details organisation))
