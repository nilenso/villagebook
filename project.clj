(defproject villagebook "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]

                 [org.postgresql/postgresql "42.2.5"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [honeysql "0.9.4"]
                 [nilenso/honeysql-postgres "0.2.6"]
                 [ragtime "0.8.0"]

                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-devel "1.7.1"]
                 [ring/ring-json "0.4.0"]

                 [bidi "2.1.6"]
                 [buddy/buddy-auth "2.2.0"]
                 [buddy/buddy-hashers "1.4.0"]

                 [aero "1.1.3"]
                 [clj-factory "0.2.1"]]

  :ring {:handler villagebook.web/dev-handler
         :init    villagebook.config/init}
  :plugins [[lein-ring "0.12.5"]]
  :main villagebook.core
  :aot [villagebook.core]
  :repl-options {:init-ns villagebook.core}

  :source-paths ["src/clj" "src/cljs"]
  :test-paths ["test/clj" "src/cljc" "test/cljs"])
