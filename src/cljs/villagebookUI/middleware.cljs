(ns villagebookUI.middleware
  (:require [villagebookUI.store :as store]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.components.login :refer [login]]))

(defn protected
  [component]
  (fn []
    (if (store/fetched?)
      (if @store/user
        [component]
        [login])
      [utils/loading])))
