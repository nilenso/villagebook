(ns villagebook.field.db-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.organisation.db :as org-db]
            [villagebook.category.db :as category-db]
            [villagebook.field.db :as db]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-fields-test
  (testing "Should create new fields belonging to a category"
    (let [{org-id :id}      (org-db/create! factory/organisation)
          {category-id :id} (category-db/create! factory/category1 org-id)
          fields            (map #(assoc % :category_id category-id) [factory/field1 factory/field2])
          reqd-fields       (db/create-fields! fields)]
      (is (= (set (map :name fields)) (set (map :name reqd-fields)))))))

(deftest invalid-create-fields-test
  (testing "Should return nil if empty list provided"
    (let [{org-id :id}      (org-db/create! factory/organisation)
          {category-id :id} (category-db/create! factory/category1 org-id)
          fields            []
          reqd-fields       (db/create-fields! fields)]
      (is (= nil reqd-fields)))))
