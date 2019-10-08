(ns villagebookUI.api.category
  (:require [ajax.core :refer [GET POST]]))

(defn ^:private create-category-api [org-id] (str "/api/organisations/" org-id "/categories"))
(defn ^:private get-categories-api [org-id] (str "/api/organisations/" org-id "/categories"))

(defn create [{:keys [category org-id handler error-handler]}]
  (POST (create-category-api org-id)
        {:body          (.stringify js/JSON (clj->js category))
         :headers       {:content-type "application/json"}
         :handler       handler
         :error-handler error-handler}))

(defn get-all [{:keys [org-id handler error-handler]}]
  (GET (get-categories-api org-id)
       {:handler         handler
        :response-format :json
        :error-handler   error-handler}))
