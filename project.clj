(defproject viclj "0.1.0-SNAPSHOT"
  :description "A wrapper around VMware Infrastructure (vSphere) Java API"
  :url "http://github.com/buerkle/viclj"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.vmware/vijava "5.5-beta"]
                 [dom4j/dom4j "1.6.1"]
                 [log4j/log4j "1.2.17"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.6"]
                                  [aprint "0.1.0"]]
                   :source-paths ["dev"]}}
  :plugins [[codox "0.8.10"]]
  :repositories {"local" ~(str (.toURI (java.io.File. "lib")))})
