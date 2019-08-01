(ns villagebook.auth.db-test
  (:require [villagebook.fixtures :refer [wrap-setup]]
            [villagebook.auth.handlers :as auth-handlers]
            [villagebook.stub :refer [user1 user2]]
            [villagebook.auth.db :as db]
            [clojure.test :refer :all]))

(use-fixtures :once wrap-setup)

(deftest auth-db-tests
  (testing "Create a user in DB."
    (let [user (db/create user1)
          email (:email user)
          user-details (dissoc user1 :password)
          created-user (db/get-by-email email)
          created-user-details (apply dissoc created-user [:password :created_at :id])]
      (is (= user-details created-user-details)))))
