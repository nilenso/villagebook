(ns villagebookUI.fetchers
  (:require [accountant.core :as accountant]
            [villagebookUI.store.user :as user-store]
            [villagebookUI.api.user :as user-api]
            [villagebookUI.store.organisations :as org-store]
            [villagebookUI.api.organisation :as org-api]
            [villagebookUI.store.categories :as category-store]
            [villagebookUI.api.category :as category-api]))


(defn fetch-user! []
  (user-api/get-data
   {:handler       user-store/add!
    :error-handler #(user-store/add! nil)
    :finally       #(user-store/fetched!)}))

(defn fetch-orgs!
  [& [selector-fn]]
  (org-api/get-all
   {:handler       (fn [res]
                     (org-store/add-all! res)
                     (when selector-fn
                       (accountant/navigate!
                        (->> (org-store/get-all)
                             selector-fn
                             :id
                             (str "/orgs/")))))
    :error-handler identity}))

(defn fetch-categories!
  [org-id & [selector-fn]]
  (category-api/get-all
   {:org-id        org-id
    :handler       (fn [res]
                     (category-store/add-all! org-id res)
                     (when selector-fn
                       (->> (category-store/get-by-org org-id)
                            selector-fn
                            category-store/set-selected!)))
    :error-handler #(category-store/add-all! org-id nil)}))
