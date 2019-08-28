(ns villagebookUI.utils
  (:require [villagebookUI.api.user :as user]
            [villagebookUI.store :as store]
            [villagebookUI.components.login :refer [login]]))

(defn loading
  []
  [:div.loading-mask
   [:div.loading-mask-center
    [:span.large-blue-spinner]]])

(defn protected
  [component]
  (fn []
    (if (store/fetched?)
      (if @store/user
        [component]
        [login])
      (do
        (user/get-user-data
         (fn [res]
           (store/add-user! res)
           (store/fetched!))
         (fn [res]
           (store/add-user! nil)
           (store/fetched!)))
        [loading]))))
