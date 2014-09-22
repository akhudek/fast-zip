(ns fast-zip.core-bench
  (:require
    [fast-zip.core :as fz]
    [clojure.zip :as z]
    [cljs.nodejs :as nodejs]))

(def Benchmark
  (nodejs/require "benchmark"))

(def big-vec (vec (repeat 10 (vec (repeat 10 (vec (range 10)))))))

(defn zip-test1
  []
  (loop [i 0, loc (z/vector-zip big-vec)]
    (if (z/end? loc)
      i
      (recur (long (if (integer? (z/node loc)) (unchecked-add-int i (z/node loc)) i)) (z/next loc)))))

(defn zip-test2
  []
  (loop [i 0, loc (fz/vector-zip big-vec)]
    (if (fz/end? loc)
      i
      (recur (long (if (integer? (fz/node loc)) (unchecked-add-int i (fz/node loc)) i)) (fz/next loc)))))

(def suite
  (.. (.Suite Benchmark)
    (add ":clojure.zip" zip-test1)
    (add ":fast-zip" zip-test2)
    (on "cycle"
      (fn [e]
        (.log js/console (js/String (.-target e)))))
    (on "complete"
      (fn [e]
        (this-as *this*
          (let [fastest (-> *this*
                          (.filter "fastest")
                          (.pluck "name")
                          (aget 0))]
            (.log js/console "Fastest is" fastest)))))))

(do
  (.log js/console "Benchmark vector zip.")
  (.run suite)
  (.exit js/process))

;; Benchmark vector zip.
;; :clojure.zip x 95.20 ops/sec ±1.75% (81 runs sampled)
;; :fast-zip x 222 ops/sec ±0.88% (90 runs sampled)
;; Fastest is :fast-zip

