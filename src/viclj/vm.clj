(ns viclj.vm
  (:require [viclj.mo :refer [call]]
            [viclj.util :refer [info->map]]))

(defn guest-info
  [vm]
  (call vm (info->map (.getGuest vm))))

(defn ip-address
  [vm]
  (.getIpAddress (.getGuest vm)))
