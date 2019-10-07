(ns villagebook.item.handlers
  (:require [clojure.spec.alpha :as s]
            [ring.util.response :as res]
            [villagebook.utils :as utils]
            [villagebook.item.models :as item-models]
            [villagebook.item.spec :as spec]))

(defn create!
  [request]
  (let [params      (:params request)
        category-id (utils/route-param->int (:category-id params))
        values      (:item params)
        user-id     (get-in request [:identity :id])]
    (if (and (s/valid? ::spec/id category-id)
             (spec/valid-item? values))
      (utils/model-to-http {:message        (item-models/create! category-id values user-id)
                            :response-codes {:success 201
                                             :error   403}})
      (res/bad-request "Invalid request"))))
