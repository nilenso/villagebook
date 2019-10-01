(ns villagebook.category.handlers-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.category.db :as db]
            [villagebook.organisation.models :as org-models]
            [villagebook.category.handlers :as handlers]
            [villagebook.user.handlers :as user]
            [villagebook.user.db :as user-db]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-tests
  (testing "Creating a category and checking if it was created."
    (let [{user-id :id}  (user-db/create! factory/user1)
          {org :success} (org-models/create! factory/organisation user-id)
          request        {:params   {:name   factory/category1
                                     :fields [factory/field1 factory/field2]
                                     :org-id (str (:id org))}
                          :identity {:id user-id}}
          response       (handlers/create! request)]
      (is (= 201 (:status response)))
      (is (= factory/category1 (get-in response [:body :name]))))))


(deftest retrieve-by-org-tests
  (testing "Should retrieve an organisation's categories"
    (let [{user-id :id}  (user-db/create! factory/user1)
          {org :success} (org-models/create! factory/organisation user-id)
          category       (db/create! factory/category1 (:id org))
          request        {:params   {:org-id (str (:id org))}
                          :identity {:id user-id}}
          response       (handlers/retrieve-by-org request)]
      (is (= 200 (:status response)))
      (is (= factory/category1 (-> (:body response)
                                   first
                                   :name))))))
