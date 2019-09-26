(ns villagebook.category.models-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.organisation.models :as org-models]
            [villagebook.category.models :as models]
            [villagebook.user.db :as user-db]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-tests
  (testing "Should create a category"
    (let [{user-id :id}  (user-db/create! factory/user1)
          {org :success} (org-models/create! factory/organisation user-id)
          message        (models/create! factory/category1 (:id org) user-id)
          category       (:success message)]
      (is (= factory/category1 (:name category))))))
