(ns villagebook.item.spec
  (:require [clojure.spec.alpha :as s]))

(s/def ::id (s/and int? #(> % 0)))
(s/def ::field_id ::id)
(s/def ::value string?)
(s/def ::values (s/keys :req-un [::value ::field_id]))

(defn valid-item?
  [fields]
  (s/valid? (s/and (s/coll-of ::values :distinct true)
                    #(if (empty? %) true (apply distinct? (mapv :field_id %)))) fields))
