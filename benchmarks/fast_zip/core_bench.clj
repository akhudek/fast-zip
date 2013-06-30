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
;Evaluation count : 73200 in 60 samples of 1220 calls.
;Execution time mean : 853.431270 µs
;Execution time std-deviation : 22.330544 µs
;Execution time lower quantile : 821.376230 µs ( 2.5%)
;Execution time upper quantile : 907.143627 µs (97.5%)
;
;Found 2 outliers in 60 samples (3.3333 %)
;low-severe	 2 (3.3333 %)
;Variance from outliers : 14.1442 % Variance is moderately inflated by outliers
;
;Case:  :fast-zip
;Evaluation count : 162180 in 60 samples of 2703 calls.
;Execution time mean : 385.903558 µs
;Execution time std-deviation : 15.064595 µs
;Execution time lower quantile : 367.073622 µs ( 2.5%)
;Execution time upper quantile : 421.526082 µs (97.5%)
;
;Found 3 outliers in 60 samples (5.0000 %)
;low-severe	 1 (1.6667 %)
;low-mild	 2 (3.3333 %)
;Variance from outliers : 25.4515 % Variance is moderately inflated by outliers
