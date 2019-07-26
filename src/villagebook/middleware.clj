(ns villagebook.middleware)

(defn wrap-authz-middleware [handler]
  (fn [req]
    (handler req)))