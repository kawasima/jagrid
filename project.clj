(defproject net.unit8/jagrid "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [garden "1.1.5"]
                 [hiccup "1.0.5"]
                 [compojure "1.4.0"]
                 [org.jsoup/jsoup "1.8.3"]
                 [environ "1.0.2"]

                 [meta-merge "0.1.1"]
                 [ring-jetty-component "0.3.0"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.1.5"]]
  :plugins [[lein-environ "1.0.2"]]
  :profiles
  {:dev [:project/dev :profiles/dev]
   :profiles/dev {}
   :project/dev {:dependencies [[reloaded.repl "0.2.1"]
                                [org.clojure/tools.namespace "0.2.11"]
                                [org.clojure/tools.nrepl "0.2.12"]
                                [eftest "0.1.0"]
                                [kerodon "0.7.0"]]
                 :source-paths ["dev"]
                 :repl-options {:init-ns user}
                 :env {:port 3000}}}
  :main jagrid.core)
