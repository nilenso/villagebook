(ns villagebook.user.models-test
  (:require [buddy.auth :refer [authenticated?]]
            [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.factory :refer [user1 user2]]
            [villagebook.user.db :as db]
            [villagebook.user.models :as models]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-user-test
  (testing "Creating a user."
    (let [message (models/create-user user1)
          email (get-in message [:success :email])]
      (is email)
      (is (not (empty? (db/get-by-email email)))))))

(deftest getting-token-test
  (let [user (db/create user1)
        {:keys [email password]} user1]
  (testing "Getting a token for user."
    (let [message (models/get-token email password)
          token   (get-in message [:success :token])
          ;; make dummy request with token for buddy auth
          header  (str "Token " token)
          request {:headers {"authorization" header} :identity {:user email}}]
        (is (authenticated? request))))))

(deftest invalid-getting-token-tests
  (let [user         (db/create user1)
        bad-email    "random@example.com"
        bad-password "wrongpassword"
        {:keys [email password]} user1]

  (testing "Getting token with invalid email (does not exist)"
    (let [message (models/get-token bad-email bad-password)
          error   (:error message)]
      (is (= error "Email not found"))))

  (testing "Getting token with invalid password."
    (let [message (models/get-token email bad-password)
          error   (:error message)]
      (is (= error "Invalid password"))))))

(deftest retrieve-user-tests
  (testing "Retrieving user by email"
    (let [user (db/create user1)
          email (:email user1)
          userdata (models/get-by-email email)]
      (is (:email userdata))
      (is (= nil (:password userdata))))))
