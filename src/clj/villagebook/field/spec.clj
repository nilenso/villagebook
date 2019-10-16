(ns villagebook.field.spec
  (:require [clojure.spec.alpha :as s]))

(s/def ::name (s/and string? (complement clojure.string/blank?)))
(s/def ::type #{"text"})
(s/def ::field (s/keys :req-un [::name]
                       :opt-un [::type]))

(s/def ::fields (s/coll-of ::field))

(defn valid-fields?
  [fields]
  (s/valid? ::fields fields))
