(ns villagebook.auth.core-test
  (:require [buddy.sign.jwt :as jwt]
            [buddy.auth :refer [authenticated?]]
            [buddy.auth.middleware :refer [wrap-authentication]]

            [villagebook.fixtures :refer [setup-once wrap-transaction]]
            [villagebook.config :as config]
            [villagebook.web :as web]
            [clojure.test :refer :all]))

(use-fixtures :once setup-once)
(use-fixtures :each wrap-transaction)

(def jwt-data {:userid 1})

(defn make-header-request
  ([data secret]
   (make-header-request data secret {}))
  ([data secret options]
   (let [header (->> (jwt/sign data secret options)
                     (format "Token %s"))]
     {:headers {"authorization" header}})))

(defn make-cookie-request
  ([data secret]
   (make-cookie-request data secret {}))
  ([data secret options]
   (let [token (jwt/sign data secret options)]
     {:cookies {"token" {:value token}}})))

(deftest custom-backend-tests
  (testing "Token backend authentication - in header"
    (let [request (make-header-request jwt-data (config/jwt-secret))
          handler (wrap-authentication identity (web/auth-backend))
          request' (handler request)]
      (is (authenticated? request'))
      (is (= (:identity request') jwt-data))))

  (testing "Token backend authentication - in cookie"
    (let [request (make-cookie-request jwt-data (config/jwt-secret))
          handler (wrap-authentication identity (web/auth-backend))
          request' (handler request)]
      (is (authenticated? request'))
      (is (= (:identity request') jwt-data)))))
