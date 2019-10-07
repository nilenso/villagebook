(ns villagebook.model-helpers-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.model-helpers :as helpers]
            [villagebook.organisation.db :as org-db]
            [villagebook.user.db :as user-db]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest permissions-valid-test
  (is (= helpers/OWNER_PERMISSION :owner))
  (is (= helpers/MEMBER_PERMISSION :member)))

(deftest is-org-owner-test?
  (testing "Checks if is-org-owner? for an owner of an organisation"
    (let [{user-id :user-id}       (user-db/create! factory/user1)
          {org-id :org-id}         (org-db/create! factory/organisation)
          {permission :permission} (org-db/add-user-as! org-id user-id helpers/OWNER_PERMISSION)]
      (is (helpers/is-org-owner? org-id user-id)))))

(deftest is-org-owner-or-member?
  (let [{org-id :org-id} (org-db/create! factory/organisation)]
    (testing "Checks if is-org-owner-or-member? for an owner of an organisation"
      (let [{user-id :user-id}       (user-db/create! factory/user1)
            {permission :permission} (org-db/add-user-as! org-id user-id helpers/OWNER_PERMISSION)]
        (is (helpers/is-org-owner-or-member? org-id user-id))))
    (testing "Checks if is-org-owner-or-member? for a member of an organisation"
      (let [{user-id :user-id}       (user-db/create! factory/user2)
            {permission :permission} (org-db/add-user-as! org-id user-id helpers/MEMBER_PERMISSION)]
        (is (helpers/is-org-owner-or-member? org-id user-id))))))
