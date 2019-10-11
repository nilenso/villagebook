(ns villagebook.item.models-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.field.db :as field-db]
            [villagebook.user.db :as user-db]
            [villagebook.organisation.models :as org-models]
            [villagebook.category.db :as category-db]
            [villagebook.item.models :as models]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-tests
  (testing "Should create an item with given fields"
    (let [user-id     (:id (user-db/create! factory/user1))
          org         (:success (org-models/create! factory/organisation user-id))
          category-id (:id (category-db/create! factory/category1
                                                (:id org)))
          fields      (field-db/create-fields! (map #(assoc % :category_id category-id) [factory/field1 factory/field2]))
          values      (for [f fields]
                        {:field_id (:id f)
                         :value    (factory/field-value)})
          item-id     (get-in (models/create! category-id values user-id) [:success :id])
          reqd-values (get-in (models/retrieve category-id item-id user-id) [:success :values])]
      (is (= (set (map :category-id reqd-values)) (set [category-id])))
      (is (= (set (map :values reqd-values)) (set (map :values values)))))))


(deftest retrieve-by-category-tests
  (testing "Should retrieve items with values in a category"
    (let [user-id     (:id (user-db/create! factory/user1))
          org         (:success (org-models/create! factory/organisation user-id))
          category-id (:id (category-db/create! factory/category1
                                                (:id org)))
          fields      (field-db/create-fields! (map #(assoc % :category_id category-id) [factory/field1 factory/field2]))
          values      (for [f fields]
                        {:field_id (:id f)
                         :value    (factory/field-value)})
          item        (:success (models/create! category-id values user-id))
          reqd-item   (->(models/retrieve-by-category category-id user-id)
                         (get-in [:success :items])
                         first)]
      (is (= (:id item)(:id reqd-item))))))
