(ns villagebookUI.fetchers
  (:require [villagebookUI.store.organisations :as org-store]
            [villagebookUI.api.organisation :as org-api]))

(defn fetch-orgs!
  [& [selector-fn]]
  (org-api/get-all
   (fn [res]
     (org-store/add-all! res)
     (when selector-fn
       (org-store/set-selected!
        (selector-fn (org-store/get-all)))))
   identity))
