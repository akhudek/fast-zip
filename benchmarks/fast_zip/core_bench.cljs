(ns fast-zip.core-bench
  (:require
   [fast-zip.core :as fz]
   [clojure.zip :as z]
   [cljs.nodejs :as nodejs]))

(def Benchmark
  (nodejs/require "benchmark"))

(def big-vec (vec (repeat 10 (vec (repeat 10 (vec (range 10)))))))

(defn zip-walk
  [zipper node next end?]
  (loop [i 0, loc zipper]
    (if (end? loc)
      i
      (recur (long (if (integer? (node loc)) (unchecked-add-int i (node loc)) i)) (next loc)))))

(defn zip-edit
  [zipper root node edit next end?]
  (loop [loc zipper]
    (if (end? loc)
      (root loc)
      (recur (next (if (integer? (node loc)) (edit loc * 10) loc))))))

(defn make-benchmark
  [id clj fz]
  (.. (.Suite Benchmark)
      (add (str ":clojure.zip." id) clj)
      (add (str ":fast-zip." id) fz)
      (on "cycle" (fn [e] (.log js/console (js/String (.-target e)))))
      (on "complete"
          (fn [e]
            (this-as
             *this*
             (let [fastest (-> *this*
                               (.filter "fastest")
                               (.pluck "name")
                               (aget 0))]
               (.log js/console "Fastest is" fastest)))))))

(def bench-walk
  (make-benchmark
   "walk"
   #(zip-walk (z/vector-zip big-vec) z/node z/next z/end?)
   #(zip-walk (fz/vector-zip big-vec) fz/node fz/next fz/end?)))

(def bench-edit
  (make-benchmark
   "edit"
   #(zip-edit (z/vector-zip big-vec) z/root z/node z/edit z/next z/end?)
   #(zip-edit (fz/vector-zip big-vec) fz/root fz/node fz/edit fz/next fz/end?)))

(do
  (.log js/console "Benchmark: vector zip walk...")
  (.run bench-walk)
  (.log js/console "Benchmark: vector zip edit...")
  (.run bench-edit)
  (.exit js/process))

;; Benchmark vector zip.
;; :clojure.zip x 95.20 ops/sec ±1.75% (81 runs sampled)
;; :fast-zip x 222 ops/sec ±0.88% (90 runs sampled)
;; Fastest is :fast-zip
