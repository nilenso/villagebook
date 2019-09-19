(ns villagebookUI.middleware
  (:require [villagebookUI.store.user :as user-store]
            [villagebookUI.components.utils :as utils]
            [villagebookUI.components.login :refer [login]]))

(defn require-login
  [component & props]
  (fn []
    (if (user-store/fetched?)
      (if (user-store/read)
        [(apply component props)]
        [login])
      [utils/loading])))
