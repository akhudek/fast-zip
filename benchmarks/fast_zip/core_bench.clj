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

;-----
;Case:  :clojure.zip
;Evaluation count : 74400 in 60 samples of 1240 calls.
;Execution time mean : 832.891667 µs
;Execution time std-deviation : 23.931702 µs
;Execution time lower quantile : 804.725806 µs ( 2.5%)
;Execution time upper quantile : 894.015706 µs (97.5%)
;
;Found 3 outliers in 60 samples (5.0000 %)
;low-severe	 3 (5.0000 %)
;Variance from outliers : 15.7905 % Variance is moderately inflated by outliers
;
;Case:  :fast-zip
;Evaluation count : 163260 in 60 samples of 2721 calls.
;Execution time mean : 386.074342 µs
;Execution time std-deviation : 13.529663 µs
;Execution time lower quantile : 369.365674 µs ( 2.5%)
;Execution time upper quantile : 415.512211 µs (97.5%)
;
;Found 6 outliers in 60 samples (10.0000 %)
;low-severe	 6 (10.0000 %)
;Variance from outliers : 22.1788 % Variance is moderately inflated by outliers