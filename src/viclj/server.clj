(ns viclj.server
  (:import [com.vmware.vim25.mo InventoryNavigator ServiceInstance]
           [java.net URI])
  (:require [viclj.mo :refer [call]]
            [viclj.util :refer [info->map]]))

(defn url
  [host]
  (let [host (if (some #(.endsWith host %) ["/sdk" "/sdk/"])
               host
               (str host "/sdk"))
        host (if (some #(.startsWith host %) ["http://" "https://"])
               host
               (str "https://" host))]
    (-> host URI. .normalize .toURL)))

(defn connect
  [host username password]
  (ServiceInstance. (url host) username password true))

(defn disconnect
  [server]
  (call server (-> server .getServerConnection .logout)))

(defn about
  "Returns a map containing the information from the server's AboutInfo"
  [server]
  (call server (info->map (.getAboutInfo server))))

(defn list-vms
  [server]
  "Returns a list of the VirtualMachine objects"
  (call server (-> server
                         .getRootFolder
                         InventoryNavigator.
                         (.searchManagedEntities "VirtualMachine"))))

(defn vm-by-name
  [server name]
  "Returns the VirtualMachine with the given name"
  (some #(when (= name (.getName %)) %) (list-vms server)))
