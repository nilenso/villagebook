(ns villagebook.routes
  (:require [bidi.ring :refer [make-handler resources]]
            [buddy.auth.middleware :refer [wrap-authorization]]

            [villagebook.middleware :refer [with-auth]]
            [villagebook.user.handlers :as user]
            [villagebook.organisation.handlers :as org]
            [villagebook.category.handlers :as category]
            [villagebook.item.handlers :as item]
            [villagebook.handlers :refer [api-handler frontend-handler]]
            [villagebook.config :as config]))

(def api-routes
  {"organisations" {:get          (with-auth org/retrieve-by-user)
                    :post         (with-auth org/create!)
                    ["/" :org-id] {:get          (with-auth org/retrieve)
                                   :delete       (with-auth org/delete!)
                                   "/categories" {:get               (with-auth category/retrieve-by-org)
                                                  :post              (with-auth category/create!)
                                                  ["/" :category-id] {:get  (with-auth item/retrieve-by-category)
                                                                      :post (with-auth item/create!)}}}}
   "user"          {:get (with-auth user/retrieve)}})


;; Setup routes
(def routes
  ["/" {"assets" (resources {:prefix "public/assets/"})
        "api/"   api-routes
        "signup" {:post user/signup}
        "login"  {:post user/login}
        true     frontend-handler}])
