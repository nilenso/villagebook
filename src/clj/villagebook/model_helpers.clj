(ns villagebook.model-helpers
  (:require [villagebook.config :as config]
            [villagebook.organisation.db :as org-db]
            [villagebook.category.db :as category-db]))

(defonce OWNER_PERMISSION :owner)
(defonce MEMBER_PERMISSION :member)

(defn is-org-owner?
  ([org-id user-id]
   (is-org-owner? (config/db-spec) org-id user-id))
  ([conn org-id user-id]
   (= (org-db/get-permission conn user-id org-id) (name OWNER_PERMISSION))))

(defn is-org-owner-or-member?
  ([org-id user-id]
   (is-org-owner-or-member? (config/db-spec) org-id user-id))
  ([conn org-id user-id]
   (let [permission (org-db/get-permission conn user-id org-id)]
     (or (= permission (name OWNER_PERMISSION))
         (= permission (name MEMBER_PERMISSION))))))

(defn is-category-owner-or-member?
  ([category-id user-id]
   (is-category-owner-or-member? (config/db-spec) category-id user-id))
  ([conn category-id user-id]
   (is-org-owner-or-member? conn
                            (:org-id (category-db/retrieve conn category-id))
                            user-id)))
