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
          request  {:params {:org-id (str id)}}
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
  (testing "Response should be empty sequence if no organisations exist for the user"
    (let [request  {:identity {:id 0}}
          response (handlers/retrieve-by-user request)]
      (is (= 200 (:status response)))
      (is (empty? (:body response))))))

(deftest delete-tests
  (testing "Should delete an organisation"
    (let [{user-id :id}      (user-db/create! factory/user1)
          {orgdata :success} (models/create! factory/organisation user-id)
          id                 (:id orgdata)
          request            {:identity {:id user-id} :params {:org-id (str id)}}
          response           (handlers/delete! request)]
      (is (= 200 (:status response))))))

(deftest invalid-delete-tests
  (testing "Should return 403 if invalid permission or organisation does not exist"
    (let [request  {:identity {:id 0} :params {:org-id (str 1)}}
          response (handlers/delete! request)]
      (is (= 403 (:status response))))))
