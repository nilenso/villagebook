(ns villagebook.field-value.db-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.organisation.db :as org-db]
            [villagebook.category.db :as category-db]
            [villagebook.field.db :as field-db]
            [villagebook.item.db :as item-db]
            [villagebook.field-value.db :as db]
            [villagebook.factory :as factory]
            [clojure.java.jdbc :as jdbc]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest add-values-tests
  (testing "Should add an item's field values"
    (let [org-id          (:id (org-db/create! factory/organisation))
          category-id     (:id (category-db/create! factory/category1 org-id))
          fields          (field-db/create-fields! (map #(assoc % :category_id category-id) [factory/field1 factory/field2]))
          item-id         (:id (item-db/create! category-id))
          value-rows      (for [f fields]
                            {:category_id (:category-id f)
                             :field_id    (:id f)
                             :item_id     item-id
                             :value       (factory/field-value)})
          reqd-value-rows (db/add-values! value-rows)]
      (is (= (set (map :category-id reqd-value-rows)) (set [category-id])))
      (is (= (set (map :values reqd-value-rows)) (set (map :values value-rows)))))))
