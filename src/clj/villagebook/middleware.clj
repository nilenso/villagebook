(ns villagebook.middleware
  (:require [buddy.auth :refer [authenticated? throw-unauthorized]]))

(defn ignore-trailing-slash
  "Modifies the request uri before calling the handler.
  Removes a single trailing slash from the end of the uri if present."
  [handler]
  (fn [request]
    (let [uri (:uri request)]
      (handler (assoc request :uri (if (and (not (= "/" uri))
                                            (.endsWith uri "/"))
                                     (subs uri 0 (dec (count uri)))
                                     uri))))))
(defn with-auth
  "Checks if request is authenticated, denies if it is not."
  [handler]
  (fn [request]
    (if (authenticated? request)
      (handler request)
      (throw-unauthorized))))
