(ns villagebookUI.api.item
  (:require [ajax.core :refer [GET POST PUT]]
            [villagebookUI.helpers :as helpers]))

(defn ^:private create-item-api [org-id category-id]
  (str "/api/organisations/" org-id "/categories/" category-id))

(defn ^:private get-all-items-api [org-id category-id]
  (str "/api/organisations/" org-id "/categories/" category-id))

(defn create [{:keys [org-id category-id item handler error-handler]}]
  (POST (create-item-api org-id category-id)
        {:body          (helpers/jsonify item)
         :headers       {:content-type "application/json"}
         :handler       handler
         :error-handler error-handler}))

(defn get-all [{:keys [org-id category-id handler error-handler]}]
  (GET (get-all-items-api org-id category-id)
       {:handler         handler
        :response-format :json
        :error-handler   error-handler}))

(defn update-item [{:keys [org-id category-id item handler error-handler]}]
  (PUT (create-item-api org-id category-id)
       {:body          (helpers/jsonify item)
        :headers       {:content-type "application/json"}
        :handler       handler
        :error-handler error-handler}))
