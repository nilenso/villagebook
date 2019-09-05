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
    (let [{user-id :id}      (user-db/create! factory/user1)
          {orgdata :success} (models/create! factory/organisation user-id)
          test-org           (assoc factory/organisation :user_id user-id)
          new-org            (first (db/retrieve-by-user user-id))]
      (is (= test-org (dissoc new-org :created_at :id :id_2 :org_id :permission))))))
