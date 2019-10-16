(ns villagebook.factory
  (:use [clj-factory.core :only [deffactory defseq factory fseq]]))

(defseq :email [n] (str "user" n "@test.com"))
(defseq :password [n] (str "password" n))
(defseq :nickname [n] (str "nickname" n))
(defseq :name [n] (str "name" n))
(defseq :orgname [n] (str "organisation" n))
(defseq :orgcolor [n] (str "color" n))
(defseq :category-name [n] (str "Category " n))
(defseq :field-name [n] (str "Field " n))
(defseq :field-value [n] (str "Field value " n))

(deffactory :user
  {:email    (fseq :email)
   :password (fseq :password)
   :nickname (fseq :password)
   :name     (fseq :name)})

(deffactory :organisation
  {:name  (fseq :orgname)
   :color (fseq :orgcolor)})

(deffactory :field
  {:name (fseq :field-name)})

(def user1 (factory :user))
(def user2 (assoc (factory :user) :name nil))
(def organisation (factory :organisation))

(def category1 (fseq :category-name))

(def field1 (factory :field))
(def field2 (factory :field))

(defn field-value []
  (fseq :field-value))
