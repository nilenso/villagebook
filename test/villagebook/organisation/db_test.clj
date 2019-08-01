(ns villagebook.organisation.db-test
  (:require [villagebook.organisation.db :as sut]
            [villagebook.stub :as stub]
            [clojure.test :refer :all]))

(deftest create-organisation
  (testing "Should create an organisation"
    (let [{:keys [id]} (sut/create {:name "Nilenso" :color "pink"})]
      (is (= stub/organisation (-> (sut/get-by-id id)
                                   (apply dissoc [:id :created_at])))))))
