(ns villagebook.category.handlers
  (:require [ring.util.response :as res]
            [clojure.edn :as edn]
            [villagebook.utils :as utils]
            [villagebook.category.models :as models]
            [villagebook.category.spec :as category-spec]))

(defn create!
  [request]
  (let [params  (:params request)
        name    (:name params)
        fields  (:fields params)
        org-id  (edn/read-string (:org-id params))
        user-id (get-in request [:identity :id])]
    (if (category-spec/valid-category? name fields org-id)
      (utils/model-to-http (models/create! name fields org-id user-id)
                           {:success 201 :error 403})
      (res/bad-request "Invalid request."))))
