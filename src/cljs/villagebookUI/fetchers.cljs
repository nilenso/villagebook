(ns villagebookUI.fetchers
  (:require [villagebookUI.store.organisations :as org-store]
            [villagebookUI.api.organisation :as org-api]))

(defn fetch-orgs! []
  (org-api/get-all
   (fn [res]
     (org-store/add-all! res))
   identity))
