(ns villagebook.organisation.handlers-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.organisation.db :as db]
            [villagebook.user.db :as user-db]
            [villagebook.organisation.handlers :as handlers]
            [villagebook.organisation.models :as models]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-tests
  (testing "Creating an organisation and checking if it was created."
    (let [request {:params factory/organisation}
          response (handlers/create! request)]
      (is (= 201 (:status response)))
      (is (= (:name factory/organisation) (get-in response [:body :name])))
      (is (not (empty? (handlers/retrieve (get-in response [:body :id]))))))))

(deftest retrieve-tests
  (testing "Retrieving an organisation"
    (let [{id :id} (db/create! factory/organisation)
          request  {:params {:id (str id)}}
          response  (handlers/retrieve request)]
      (is (= 200 (:status response)))
      (is (= (:name factory/organisation) (get-in response [:body :name])))
      (is (not (empty? (handlers/retrieve (get-in response [:body :id]))))))))

(deftest retrieve-by-user-tests
  (testing "Should retrieve a user's orgs given a user id"
    (let [{user-id :id}      (user-db/create! factory/user1)
          {orgdata :success} (models/create! factory/organisation user-id)
          required-keys      (keys orgdata)
          request            {:identity {:id user-id}}
          response           (handlers/retrieve-by-user request)]
      (is (= 200 (:status response)))
      (is (= orgdata (-> (:body response)
                         first
                         (select-keys required-keys)))))))

(deftest invalid-retrieve-by-user-tests
  (testing "Response should be valid json (vector, not a clj seq) if no organisations exist for the user"
    (let [request  {:identity {:id 0}}
          response (handlers/retrieve-by-user request)]
      (is (= 200 (:status response)))
      (is (vector? (:body response)))
      (is (empty? (:body response))))))
