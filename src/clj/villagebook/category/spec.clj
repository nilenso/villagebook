(ns villagebook.category.spec
  (:require [clojure.spec.alpha :as s]
            [villagebook.field.spec :as field-spec]))

(s/def ::org-id (s/and int? #(> % 0)))
(s/def ::name (s/and string? (complement clojure.string/blank?)))

(defn valid-category?
  [name fields org-id]
  (and (s/valid? ::name name)
       (s/valid? ::org-id org-id)
       (field-spec/valid-fields? fields)))
