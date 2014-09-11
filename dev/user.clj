(ns user
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application."
  (:require
   [clojure.java.io :as io]
   [clojure.java.javadoc :refer [javadoc]]
   [clojure.pprint :refer [pprint]]
   [clojure.reflect :refer [reflect]]
   [clojure.repl :refer [apropos dir doc find-doc pst source]]
   [clojure.set :as set]
   [clojure.string :as str]
   [clojure.test :as test]
   [clojure.tools.namespace.repl :refer [refresh refresh-all]]
   [aprint.core :refer [aprint ap]]
   [viclj.server :as server]))

(def system
  "A Var containing an object representing the application under
  development."
  nil)

(defn init
  "Creates and initializes the system under development in the Var
  #'system."
  []
  ;; TODO
  )

(defn start
  "Starts the system running, updates the Var #'system."
  []
  (let [host (System/getenv "VI_HOST")
        user (System/getenv "VI_USER")
        pass (System/getenv "VI_PASS")]
    (if (some nil? [host user pass])
      nil
      (alter-var-root #'system
                      (constantly
                       (server/connect host user pass))))))

(defn stop
  "Stops the system if it is currently running, updates the Var
  #'system."
  []
  (alter-var-root #'system (fn [s] (when s (server/disconnect s)))))

(defn go
  "Initializes and starts the system running."
  []
  (init)
  (if (nil? (start))
    "Missing VI_HOST, VI_USER and/or VI_PASS environment variables"
    :ready))

(defn reset
  "Stops the system, reloads modified source files, and restarts it."
  []
  (stop)
  (refresh :after 'user/go))
