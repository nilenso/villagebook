(ns villagebookUI.api.category
  (:require [ajax.core :refer [POST]]))

(defn ^:private create-category-api [org-id] (str "/api/organisations/" org-id "/categories"))

(defn create [category org-id handler error-handler]
  (POST (create-category-api org-id)
        {:body          (.stringify js/JSON (clj->js category))
         :headers       {:content-type "application/json"}
         :handler       handler
         :error-handler error-handler}))
