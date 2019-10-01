(ns villagebookUI.fetchers
  (:require [accountant.core :as accountant]
            [villagebookUI.store.user :as user-store]
            [villagebookUI.api.user :as user-api]
            [villagebookUI.store.organisations :as org-store]
            [villagebookUI.api.organisation :as org-api]))


(defn fetch-user! []
  (user-api/get-data
   (fn [res]
     (user-store/add! res))
   (fn [res]
     (user-store/add! nil))
   #(user-store/fetched!)))

(defn fetch-orgs!
  [& [selector-fn]]
  (org-api/get-all
   (fn [res]
     (org-store/add-all! res)
     (when selector-fn
       (accountant/navigate!
        (->> (org-store/get-all)
             selector-fn
             :id
             (str "/orgs/")))))
   identity))
