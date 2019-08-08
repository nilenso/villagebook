(ns villagebook.factory
  (:use [clj-factory.core :only [deffactory defseq factory fseq]]))

(defseq :orgname [n] (str "organisation" n))
(defseq :orgcolor [n] (str "color" n))
(deffactory :organisation
  {:name  (fseq :orgname)
   :color (fseq :orgcolor)})

(def organisation (factory :organisation))
