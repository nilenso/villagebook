(ns villagebookUI.middleware
  (:require [villagebookUI.store.user :as user-store]
            [accountant.core :as accountant]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.components.login :refer [login]]))

(defn require-login
  [component]
  (fn []
    (if (user-store/fetched?)
      (if (user-store/read)
        [component]
        [login])
      [utils/loading])))

(defn redirect-logged-in
  [component redirect-path]
  (fn []
    (if (user-store/fetched?)
      (if (user-store/read)
        (do
          (accountant/navigate! redirect-path)
          [:div])
        [component])
      [utils/loading])))
