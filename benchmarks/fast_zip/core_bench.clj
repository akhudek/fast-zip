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

;; 0.3.0
;Goal:  Benchmark vector zip.
;-----
;Case:  :clojure.zip
;Evaluation count : 68400 in 60 samples of 1140 calls.
;Execution time mean : 879.080906 µs
;Execution time std-deviation : 11.090444 µs
;Execution time lower quantile : 864.859649 µs ( 2.5%)
;Execution time upper quantile : 907.783816 µs (97.5%)
;
;Found 3 outliers in 60 samples (5.0000 %)
;low-severe	 3 (5.0000 %)
;Variance from outliers : 1.6389 % Variance is slightly inflated by outliers
;
;Case:  :fast-zip
;Evaluation count : 226980 in 60 samples of 3783 calls.
;Execution time mean : 259.947207 µs
;Execution time std-deviation : 3.051484 µs
;Execution time lower quantile : 255.424266 µs ( 2.5%)
;Execution time upper quantile : 266.486446 µs (97.5%)