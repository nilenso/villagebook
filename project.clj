(defproject villagebook "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]

                 [org.postgresql/postgresql "42.2.5"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [honeysql "0.9.4"]
                 [toucan "1.12.0"]
                 [ragtime "0.8.0"]

                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-devel "1.7.1"]
                 [bidi "2.1.6"]
                 [ring/ring-json "0.4.0"]

                 [environ "1.1.0"]]

  :ring {:handler villagebook.core/app-handler}
  :plugins [[lein-ring "0.12.5"]]
  :repl-options {:init-ns villagebook.core})
