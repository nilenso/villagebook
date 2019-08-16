(ns villagebook.auth.db-test
  (:require [villagebook.fixtures :refer [wrap-setup]]
            [villagebook.factory :refer [user1 user2]]
            [villagebook.auth.db :as auth-db]
            [clojure.test :refer :all]))

(use-fixtures :each wrap-setup)

(deftest create-and-get-user-test
  (testing "Creating and getting a user in DB."
    (let [user (auth-db/create user1)
          email (:email user)
          user-details (dissoc user1 :password)
          created-user (auth-db/get-by-email email)
          created-user-details (apply dissoc created-user [:password :created_at :id])]
      (is (= user-details created-user-details)))))
