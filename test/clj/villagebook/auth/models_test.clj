(ns villagebook.auth.models-test
  (:require [villagebook.fixtures :refer [wrap-setup]]
            [villagebook.factory :refer [user1 user2]]
            [villagebook.auth.db :as auth-db]
            [villagebook.auth.models :as auth-models]
            [clojure.test :refer :all]
            [buddy.auth :refer [authenticated?]]
            [villagebook.auth.handlers :as auth]))

(use-fixtures :each wrap-setup)

(deftest create-user-test
  (testing "Creating a user."
    (let [message (auth-models/create-user user1)
          email (get-in message [:success :email])]
      (is email)
      (is (not (empty? (auth-db/get-by-email email)))))))

(deftest getting-token-test
  (let [user (auth-db/create user1)
        {:keys [email password]} user1]
  (testing "Getting a token for user."
    (let [message (auth-models/get-token email password)
          token   (get-in message [:success :token])
          ;; make dummy request with token for buddy auth
          header  (str "Token " token)
          request {:headers {"authorization" header} :identity {:user email}}]
        (is (authenticated? request))))))

(deftest invalid-getting-token-tests
  (let [user         (auth-db/create user1)
        bad-email    "random@example.com"
        bad-password "wrongpassword"
        {:keys [email password]} user1]

  (testing "Getting token with invalid email (does not exist)"
    (let [message (auth-models/get-token bad-email bad-password)
          error   (:error message)]
      (is (= error "Email not found"))))

  (testing "Getting token with invalid password."
    (let [message (auth-models/get-token email bad-password)
          error   (:error message)]
      (is (= error "Invalid password"))))))
