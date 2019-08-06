(ns villagebook.utils)

(def email-pattern #"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")

(def alpha-num-pattern #"^[a-zA-Z0-9]+$")

(defn required
  [value]
  (and (string? value)
       (not-empty value)))
