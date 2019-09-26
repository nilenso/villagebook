(ns villagebook.category.spec
  (:require [clojure.spec.alpha :as s]))

(s/def ::org-id (s/and int? #(> % 0)))
(s/def ::name (s/and string? (complement clojure.string/blank?)))

(defn valid-category?
  [name org-id]
  (and (s/valid? ::org-id org-id)
       (s/valid? ::name name)))
