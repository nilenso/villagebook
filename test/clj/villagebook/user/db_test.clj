(ns villagebook.user.db-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.factory :refer [user1 user2]]
            [villagebook.user.db :as db]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-and-get-user-test
  (testing "Creating and getting a user in DB."
    (let [user (db/create! user1)
          email (:email user)
          user-details (dissoc user1 :password)
          created-user (db/retrieve-by-email email)
          created-user-details (apply dissoc created-user [:password :created_at :id])]
      (is (= user-details created-user-details)))))
