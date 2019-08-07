(ns villagebook.organisation.db-test
  (:require [villagebook.organisation.db :as sut]
            [villagebook.factory :as factory]
            [clojure.test :refer :all]))

(deftest create-organisation
  (testing "Should create an organisation"
    (let [{:keys [id]} (sut/create factory/organisation)]
      (is (= factory/organisation (as-> (sut/get-by-id id) $
                                   (apply dissoc $ [:id :created_at])))))))
