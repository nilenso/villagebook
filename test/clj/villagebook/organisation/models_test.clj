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
