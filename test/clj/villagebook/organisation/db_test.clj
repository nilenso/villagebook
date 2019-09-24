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
      (is (= factory/organisation (-> (db/retrieve id)
                                      (dissoc :id :created_at)))))))

(deftest add-user-as-owner-tests
  (testing "Should make the user owner of the organisation"
    (let [{user-id :user-id}       (user-db/create! factory/user1)
          {org-id :org-id}         (db/create! factory/organisation)
          {permission :permission} (db/add-user-as! org-id user-id :owner)]
        (is (= permission "owner")))))

(deftest add-user-as-member-tests
  (testing "Should make the user member of the organisation"
    (let [{user-id :user-id}       (user-db/create! factory/user1)
          {org-id :org-id}         (db/create! factory/organisation)
          {permission :permission} (db/add-user-as! org-id user-id :member)]
      (is (= permission "member")))))

(deftest add-user-as-none-tests
  (testing "Should set permission to none"
    (let [{user-id :user-id}       (user-db/create! factory/user1)
          {org-id :org-id}         (db/create! factory/organisation)
          {permission :permission} (db/add-user-as! org-id user-id :none)]
        (is (= permission "none")))))

(deftest add-user-as-invalid-tests
  (testing "Should fail on roles other than in the enum"
    (let [{user-id :user-id}       (user-db/create! factory/user1)
          {org-id :org-id}         (db/create! factory/organisation)
          {permission :permission} (db/add-user-as! org-id user-id "make-me-owner!")]
      (is (= permission "none")))))


(deftest retrieve-tests
 (let [{id :id}      (db/create! factory/organisation)
       required-keys (keys factory/organisation)]
   (testing "Should retrieve organisation by id"
     (is (= factory/organisation (-> (db/retrieve id)
                                     (select-keys required-keys)))))

   (testing "Should retrieve list of all organisations"
     (is (= factory/organisation (-> (db/retrieve)
                                     first
                                     (select-keys required-keys)))))))

(deftest retrieve-by-user-tests
  (testing "Should retrieve list of user's organisations with permissions"
    (let [{user-id :id}            (user-db/create! factory/user1)
          {org-id :id}             (db/create! factory/organisation)
          {permission :permission} (db/add-user-as! org-id user-id :member)
          required-keys            (keys factory/organisation)]
      (is (= factory/organisation (-> (db/retrieve-by-user user-id)
                                      first
                                      (select-keys required-keys)))))))

(deftest delete-tests
  (let [{user-id :id} (user-db/create! factory/user1)
        {org-id :id}  (db/create! factory/organisation)
        permission    (db/add-user-as! org-id user-id :owner)
        deleted-row   (db/delete! org-id)]

    (testing "Should delete an organisation by it's id"
      (is (= '(1) deleted-row))
      (is (= nil (db/retrieve org-id))))

    (testing "All permissions on the organisation should be deleted on cascade"
      (is (empty? (db/retrieve-by-user user-id))))))

(deftest get-permission-tests
  (testing "Should retrieve user's permission on an organisation"
    (let [{user-id :id}                 (user-db/create! factory/user1)
          {org-id :id}                  (db/create! factory/organisation)
          {reqd-permission :permission} (db/add-user-as! org-id user-id :owner)
          permission                    (db/get-permission user-id org-id)]
      (is (= permission reqd-permission)))))
