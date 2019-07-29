(ns villagebook.organisation.db-test
  (:require [villagebook.organisation.db :as sut]
            [clojure.test :refer :all]))

(deftest create-organisation
  (testing "Should create an organisation"
    (let [{:keys [id]} (sut/create {:name "Nilenso" :color "pink"})]
      (is (= {:id id
              :name "Nilenso"
              :color "pink"} (-> (sut/get-by-id id)
                                 (dissoc :created_at)))))))
