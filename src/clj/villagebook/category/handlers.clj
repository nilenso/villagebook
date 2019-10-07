(ns villagebook.category.handlers
  (:require [ring.util.response :as res]
            [villagebook.utils :as utils]
            [villagebook.category.models :as models]
            [villagebook.category.spec :as category-spec]
            [villagebook.organisation.spec :as org-spec]
            [clojure.spec.alpha :as s]))

(defn create!
  [request]
  (let [params  (:params request)
        name    (:name params)
        fields  (:fields params)
        org-id  (utils/route-param->int (:org-id params))
        user-id (get-in request [:identity :id])]
    (if (category-spec/valid-category? name fields org-id)
      (utils/model-to-http {:message        (models/create! name fields org-id user-id)
                            :response-codes {:success 201
                                             :error   403}})
      (res/bad-request "Invalid request."))))

(defn retrieve-by-org
  [request]
  (let [org-id  (utils/route-param->int (get-in request [:params :org-id]))
        user-id (get-in request [:identity :id])]
    (if (s/valid? ::org-spec/id org-id)
      (utils/model-to-http {:message        (models/retrieve-by-org org-id user-id)
                            :response-codes {:success 200
                                             :error   403}})
      (res/bad-request "Invalid request."))))
