(ns fast-zip.core-bench
  (:require
    [fast-zip.core :as fz]
    [clojure.zip :as z]
    [perforate.core :refer :all]))

(set! *warn-on-reflection* true)

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

(defgoal vector-zip-walk-bench "Benchmark vector zip walk.")

(defcase vector-zip-walk-bench :clojure.zip
  [] (zip-walk (z/vector-zip big-vec) z/node z/next z/end?))

(defcase vector-zip-walk-bench :fast-zip
  [] (zip-walk (fz/vector-zip big-vec) fz/node fz/next fz/end?))

(defgoal vector-zip-edit-bench "Benchmark vector zip edit.")

(defcase vector-zip-edit-bench :clojure.zip
  [] (zip-edit (z/vector-zip big-vec) z/root z/node z/edit z/next z/end?))

(defcase vector-zip-edit-bench :fast-zip
  [] (zip-edit (fz/vector-zip big-vec) fz/root fz/node fz/edit fz/next fz/end?))
