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
;Case:  :fast-zip
;Evaluation count : 57900 in 60 samples of 965 calls.
;Execution time mean : 1.061294 ms
;Execution time std-deviation : 18.324036 µs
;Execution time lower quantile : 1.040463 ms ( 2.5%)
;Execution time upper quantile : 1.101971 ms (97.5%)
;
;Found 6 outliers in 60 samples (10.0000 %)
;low-severe	 5 (8.3333 %)
;low-mild	 1 (1.6667 %)
;Variance from outliers : 6.2784 % Variance is slightly inflated by outliers
;
;Case:  :clojure.zip
;Evaluation count : 22140 in 60 samples of 369 calls.
;Execution time mean : 2.742761 ms
;Execution time std-deviation : 58.216480 µs
;Execution time lower quantile : 2.667016 ms ( 2.5%)
;Execution time upper quantile : 2.867317 ms (97.5%)
;
;Found 1 outliers in 60 samples (1.6667 %)
;low-severe	 1 (1.6667 %)
;Variance from outliers : 9.4220 % Variance is slightly inflated by outliers
