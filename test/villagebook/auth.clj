(ns villagebook.auth
  (:require [villagebook.auth.handlers :as auth-handlers]
            [villagebook.auth.db :as auth-db]
            [villagebook.stub :refer [user1 user2]]
            [clojure.test :refer :all]))

(deftest signup-tests
  (testing "Signing up as user 1"
    (let [request {:params user1}
          response (auth-handlers/signup request)]
      (is (= 201 (:status response)))
      (is (= (:email user1) (get-in response [:body :email])))
      (is (not (empty? (auth-db/get-user-by-email (get-in response [:body :email])))))))

  (testing "Signing up as user 2 (with optional)"
    (let [request  {:params user2}
          response (auth-handlers/signup request)]
      (is (= 201 (:status response)))
      (is (= (:email user2) (get-in response [:body :email])))
      (is (not (empty? (auth-db/get-user-by-email (get-in response [:body :email])))))))

  (testing "Signing up with invalid request")
    (let [request {}
          response (auth-handlers/login request)]
      (is (= 400 (:status response)))))
