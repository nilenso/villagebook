(ns villagebook.factory
  (:use [clj-factory.core :only [deffactory defseq factory fseq]]))

(defseq :email [n] (str "user" n "@test.com"))
(defseq :password [n] (str "password" n))
(defseq :nickname [n] (str "nickname" n))
(defseq :name [n] (str "name" n))

(deffactory :user
  {:email    (fseq :email)
   :password (fseq :password)
   :nickname (fseq :password)
   :name     (fseq :name)})

(def user1 (factory :user))
(def user2 (assoc (factory :user) :name nil))
