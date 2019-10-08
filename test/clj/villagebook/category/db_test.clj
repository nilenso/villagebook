(ns villagebook.category.db-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.organisation.db :as org-db]
            [villagebook.category.db :as db]
            [clojure.java.jdbc :as jdbc]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-tests
  (testing "Should create a new category"
    (let [{org-id :id}  (org-db/create! factory/organisation)
          reqd-category (-> (db/create! factory/category1 org-id)
                            :name)]
      (is (= factory/category1 reqd-category)))))

(deftest retrieve-tests
  (testing "Should retrieve an organisation's categories"
    (let [{org-id :id}  (org-db/create! factory/organisation)
          reqd-category (-> (db/create! factory/category1 org-id)
                            :name)]
      (is (= factory/category1 (-> (db/retrieve-by-org org-id)
                                   first
                                   :name))))))
