(ns villagebook.field.models-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.organisation.db :as org-db]
            [villagebook.category.db :as category-db]
            [villagebook.field.models :as models]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-fields-test
  (testing "Should create new fields belonging to a category"
    (let [{org-id :id}           (org-db/create! factory/organisation)
          {category-id :id}      (category-db/create! factory/category1 org-id)
          fields                 [factory/field1 factory/field2]
          {reqd-fields :success} (models/create-fields! category-id fields)]
      (is (= (set (map :name fields)) (set (map :name reqd-fields)))))))
