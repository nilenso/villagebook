(ns villagebook.item.db-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.organisation.db :as org-db]
            [villagebook.category.db :as category-db]
            [villagebook.factory :as factory]
            [villagebook.item.db :as db]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-tests
  (testing "Should create an item"
    (let [{org-id :id}                    (org-db/create! factory/organisation)
          {category-id :id}               (category-db/create! factory/category1 org-id)
          {reqd-category-id :category-id} (db/create! category-id)]
      (is (= category-id reqd-category-id)))))
