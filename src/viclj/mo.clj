(ns viclj.mo
  "Various functions to work with ManagedObjects")

(defn connected?
  "Returns `true' if the managed object is connected to a VMWare instance."
  [mo]
  (some? (some-> mo .getServerConnection .getServiceInstance)))

(defmacro call
  [mo & body]
  "Runs the body when the managed object is connected to the VMWare instance."
  `(when (connected? ~mo)
     ~@body))
