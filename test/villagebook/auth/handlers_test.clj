(ns villagebook.auth.handlers-test
  (:require [villagebook.fixtures :refer [wrap-setup]]
            [villagebook.stub :refer [user1 user2]]
            [villagebook.auth.db :as auth-db]
            [villagebook.auth.handlers :as auth-handlers]
            [clojure.test :refer :all]))

(use-fixtures :each wrap-setup)

(deftest signup-tests
  (testing "Signing up"
    (let [request  {:params user1}
          response (auth-handlers/signup request)]
      (is (= 201 (:status response)))
      (is (= (:email user1) (get-in response [:body :email])))
      (is (not (empty? (auth-db/get-by-email (get-in response [:body :email])))))))

  (testing "Signing up as user 2 (with optional details)"
    (let [request  {:params user2}
          response (auth-handlers/signup request)]
      (is (= 201 (:status response)))
      (is (= (:email user2) (get-in response [:body :email])))
      (is (not (empty? (auth-db/get-by-email (get-in response [:body :email]))))))))

(deftest invalid-signup-tests
  (testing "Signing up with invalid request"
    (let [request  {}
          response (auth-handlers/signup request)]
      (is (= 400 (:status response))))))

(deftest login-tests
  (testing "Logging in as user 1"
    (let [user (auth-db/create user1)
          request  {:params user1}
          response (auth-handlers/login request)]
      (is (= 200 (:status response)))
      (is (not (nil? (get-in response [:body :token])))))))

(deftest invalid-login-tests
  (let [user (auth-db/create user1)]
  (testing "Logging in with invalid request"
    (let [request  {}
          response (auth-handlers/login request)]
        (is (= 400 (:status response)))))

  (testing "Logging in with invalid request - missing password"
    (let [request  {:params (dissoc user1 :password)}
          response (auth-handlers/login request)]
      (is (= 400 (:status response)))))

  (testing "Logging in as user 1 with incorrect password"
    (let [request  {:params (assoc user1 :password "wrongpassword")}
          response (auth-handlers/login request)]
        (is (= 401 (:status response)))))

  (testing "Logging in with invalid email (does not exist)"
    (let [request  {:params (assoc user1 :email "random@example.org")}
          response (auth-handlers/login request)]
        (is (= 401 (:status response)))))))
