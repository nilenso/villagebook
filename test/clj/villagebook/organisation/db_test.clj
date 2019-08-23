(ns villagebook.organisation.db-test
  (:require [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.organisation.db :as sut]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(deftest create-organisation
  (testing "Should create an organisation"
    (let [{:keys [id]} (sut/create factory/organisation)]
      (is (= factory/organisation (-> (sut/get-by-id id)
                                    (dissoc :id :created_at)))))))
