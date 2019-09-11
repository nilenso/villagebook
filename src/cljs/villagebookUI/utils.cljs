(ns villagebookUI.utils
  (:require [villagebookUI.api.user :as user]
            [villagebookUI.store :as store]
            [villagebookUI.components.utils :refer [loading]]
            [villagebookUI.components.login :refer [login]]))

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
           (store/fetched!)))
        [loading]))))
