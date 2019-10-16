(ns villagebook.item.handlers-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.user.db :as user-db]
            [villagebook.category.db :as category-db]
            [villagebook.field.db :as field-db]
            [villagebook.organisation.models :as org-models]
            [villagebook.item.handlers :as handlers]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-test
  (testing "Should create an item in a category with given values"
    (let [user-id     (:id (user-db/create! factory/user1))
          org         (:success (org-models/create! factory/organisation user-id))
          category-id (:id (category-db/create! factory/category1
                                                (:id org)))
          fields      (field-db/create-fields! (map #(assoc % :category_id category-id) [factory/field1 factory/field2]))
          item        (for [f fields]
                        {:field_id (:id f)
                         :value    (factory/field-value)})
          request     {:params   {:category-id (str category-id)
                                  :item        item}
                       :identity {:id user-id}}
          response    (handlers/create! request)]
      (is (= 201 (:status response))))))
