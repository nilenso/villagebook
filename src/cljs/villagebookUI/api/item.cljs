(ns villagebookUI.api.item
  (:require [ajax.core :refer [POST]]
            [villagebookUI.helpers :as helpers]))

(defn ^:private create-item-api [org-id category-id]
  (str "/api/organisations/" org-id "/categories/" category-id))

(defn create [{:keys [org-id category-id item handler error-handler]}]
  (POST (create-item-api org-id category-id)
        {:body          (helpers/jsonify item)
         :headers       {:content-type "application/json"}
         :handler       handler
         :error-handler error-handler}))
