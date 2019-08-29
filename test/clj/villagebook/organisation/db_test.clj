(ns villagebook.organisation.db-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.user.db :as user-db]
            [villagebook.organisation.db :as db]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-tests
  (testing "Should create an organisation"
    (let [{:keys [id]} (db/create! factory/organisation)]
      (is (= factory/organisation (-> (db/get-by-id id)
                                      (dissoc :id :created_at)))))))

(deftest add-user-as-owner-tests
  (testing "Should make the user owner of the organisation"
    (let [{user-id :user-id} (user-db/create factory/user1)
          {org-id :org-id}   (db/create! factory/organisation)
          permission         (db/add-user-as! org-id user-id "owner")]
        (is (= (:permission permission) "owner")))))

(deftest add-user-as-member-tests
  (testing "Should make the user member of the organisation"
    (let [{user-id :user-id} (user-db/create factory/user1)
          {org-id :org-id}   (db/create! factory/organisation)
          permission         (db/add-user-as! org-id user-id "member")]
      (is (= (:permission permission) "member")))))

(deftest add-user-as-none-tests
  (testing "Should set permission to none"
    (let [{user-id :user-id} (user-db/create factory/user1)
          {org-id :org-id}   (db/create! factory/organisation)
          permission         (db/add-user-as! org-id user-id "none")]
        (is (= (:permission permission) "none")))))

(deftest add-user-as-invalid-tests
  (testing "Should fail on roles other than in the enum"
    (let [{user-id :user-id} (user-db/create factory/user1)
          {org-id :org-id}   (db/create! factory/organisation)]
      (is (thrown? Exception (db/add-user-as! org-id user-id "make-me-owner!"))))))
