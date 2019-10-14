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
          reqd-values (get-in (models/create! category-id values user-id) [:success :fields])]
      ;TODO: Check by retrieve
      (is (= (count reqd-values) (count values))))))
