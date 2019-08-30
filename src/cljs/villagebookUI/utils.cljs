(ns villagebookUI.utils
  (:require [villagebookUI.api.user :as user-api]
            [villagebookUI.store.user :as user-store]
            [villagebookUI.components.login :refer [login]]))

(defn loading
  []
  [:div.loading-mask
   [:div.loading-mask-center
    [:span.large-blue-spinner]]])

(defn protected
  [component]
  (fn []
    (if (user-store/fetched?)
      (if (user-store/get)
        [component]
        [login])
      (do
        (user-api/get-data
         (fn [res]
           (user-store/add! res)
           (user-store/fetched!))
         (fn [res]
           (user-store/add! nil)
           (user-store/fetched!)))
        [loading]))))
