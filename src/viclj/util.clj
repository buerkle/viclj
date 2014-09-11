(ns viclj.util
  (:import [com.vmware.vim25 DynamicData]))

(declare info->map)

(defn- info?
  [obj]
  (and
   (instance? DynamicData obj)))

(defn- info-array?
  [obj]
  (and
   (.isArray (class obj))
   (> (count obj) 0)
   (info? (aget obj 0))))

(defn- info-array->map
  [obj]
  (vec (map info->map obj)))

(defn info->map
  "Converts a vijava info object (eg. AboutInfo) into a map"
  [obj]
  (cond
   (nil? obj) nil
   (info-array? obj) (info-array->map obj)
   :else (let [obj (into {} (seq (bean obj)))]
           (reduce-kv (fn [result k v]
                        (cond
                         (or (= k :class) (nil? v)) (dissoc result k)
                         (info? v) (assoc result k (info->map v))
                         (info-array? v) (assoc result k (info-array->map v))
                         (instance? Enum v) (assoc result k (keyword (str v)))
                         :else result))
                      obj obj))))
