(ns villagebook.organisation.handlers-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.organisation.handlers :as handlers]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-tests
  (testing "Creating an organisation and checking if it was created."
     (let [request {:params factory/organisation}
          response (handlers/create! request)]
      (is (= 201 (:status response)))
      (is (= (:name factory/organisation) (get-in response [:body :name])))
      (is (not (empty? (handlers/retrieve (get-in response [:body :id]))))))))
