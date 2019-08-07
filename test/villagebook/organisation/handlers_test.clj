(ns villagebook.organisation.handlers-test
  (:require [villagebook.organisation.handlers :as sut]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(deftest create-organisation
  (testing "Creating an organisation and checking if it was created."
     (let [request {:params factory/organisation}
          response (sut/create-organisation request)]
      (is (= 201 (:status response)))
      (is (= (:name factory/organisation) (get-in response [:body :name])))
      (is (not (empty? (sut/get-by-id (get-in response [:body :id]))))))))
