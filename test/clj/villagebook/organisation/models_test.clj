(ns villagebook.organisation.models-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.user.db :as user-db]
            [villagebook.organisation.db :as db]
            [villagebook.organisation.models :as models]
            [clojure.test :refer :all]
            [villagebook.factory :as factory]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-test
  (testing "Should create an organisation and add a user as owner"
    (let [{user-id :id}      (user-db/create factory/user1)
          {orgdata :success} (models/create! factory/organisation user-id)]
      (is (= factory/organisation (dissoc orgdata :created_at :id))))))

(deftest retrieve-test
  (testing "Should retrieve an organisation given it's id"
    (let [{org-id :id}       (db/create! factory/organisation)
          {orgdata :success} (models/retrieve org-id)]
      (is (= factory/organisation (dissoc orgdata :created_at :id)))))
  (testing "Should return an error map if organisation does not exist"
    (let [{error :error} (models/retrieve 0)]
      (is error))))
