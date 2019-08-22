(ns villagebook.organisation.db-test
  (:require [villagebook.fixtures :refer [wrap-setup]]
            [villagebook.organisation.db :as sut]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :each wrap-setup)

(deftest create-organisation
  (testing "Should create an organisation"
    (let [{:keys [id]} (sut/create factory/organisation)]
      (is (= factory/organisation (-> (sut/get-by-id id)
                                    (dissoc :id :created_at)))))))
