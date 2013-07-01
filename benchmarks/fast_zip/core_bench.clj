(ns fast-zip.core-bench
  (:require
    [fast-zip.core :as fz]
    [clojure.zip :as z]
    [perforate.core :refer :all]))

(set! *warn-on-reflection* true)

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

(defgoal vector-zip-bench "Benchmark vector zip.")

(defcase vector-zip-bench :clojure.zip
  [] (zip-test1))

(defcase vector-zip-bench :fast-zip
  [] (zip-test2))

;Goal:  Benchmark vector zip.
;-----
;Case:  :clojure.zip
;Evaluation count : 75480 in 60 samples of 1258 calls.
;Execution time mean : 805.666773 µs
;Execution time std-deviation : 4.815877 µs
;Execution time lower quantile : 797.942766 µs ( 2.5%)
;Execution time upper quantile : 816.578299 µs (97.5%)
;
;Found 2 outliers in 60 samples (3.3333 %)
;low-severe	 2 (3.3333 %)
;Variance from outliers : 1.6389 % Variance is slightly inflated by outliers
;
;Case:  :fast-zip
;Evaluation count : 297900 in 60 samples of 4965 calls.
;Execution time mean : 202.892179 µs
;Execution time std-deviation : 848.456881 ns
;Execution time lower quantile : 201.212286 µs ( 2.5%)
;Execution time upper quantile : 204.187311 µs (97.5%)
;
;Found 1 outliers in 60 samples (1.6667 %)
;low-severe	 1 (1.6667 %)
;Variance from outliers : 1.6389 % Variance is slightly inflated by outliers