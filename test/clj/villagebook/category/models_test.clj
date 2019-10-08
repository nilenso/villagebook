(ns villagebook.category.models-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.organisation.models :as org-models]
            [villagebook.category.models :as models]
            [villagebook.category.db :as db]
            [villagebook.user.db :as user-db]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-tests
  (testing "Should create a category with given list of fields"
    (let [{user-id :id}  (user-db/create! factory/user1)
          {org :success} (org-models/create! factory/organisation user-id)
          message        (models/create! factory/category1
                                         [factory/field1 factory/field2]
                                         (:id org)
                                         user-id)
          category       (:success message)]
      (is (= factory/category1 (:name category))))))

(deftest retrieve-by-org-tests
  (testing "Should retrieve an organisation's categories"
    (let [{user-id :id}         (user-db/create! factory/user1)
          {org :success}        (org-models/create! factory/organisation user-id)
          reqd-category         (-> (db/create! factory/category1 (:id org))
                                    :name)
          {categories :success} (models/retrieve-by-org (:id org) user-id)]
      (is (= reqd-category (-> categories
                               first
                               :name))))))
