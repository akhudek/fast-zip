(ns fast-zip.core-test
  (:require #_[clojure.test :refer :all]
            [fast-zip.core :as fz]
            [clojure.zip :as z]
            [criterium.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))


(def big-vec (vec (repeat 10 (vec (repeat 10 (vec (range 10)))))))

(bench
  (loop [i 0, loc (z/vector-zip big-vec)]
    (if (z/end? loc)
      i
      (recur (if (integer? (z/node loc)) (+ i (z/node loc)) i) (z/next loc)))))

;Evaluation count : 55080 in 60 samples of 918 calls.
;Execution time mean : 1.098447 ms
;Execution time std-deviation : 4.697365 µs
;Execution time lower quantile : 1.090690 ms ( 2.5%)
;Execution time upper quantile : 1.107148 ms (97.5%)
;Overhead used : 1.853817 ns
;
;Found 1 outliers in 60 samples (1.6667 %)
;low-severe	 1 (1.6667 %)
;Variance from outliers : 1.6389 % Variance is slightly inflated by outliers

(bench
  (loop [i 0, loc (fz/vector-zip big-vec)]
    (if (fz/end? loc)
      i
      (recur (if (integer? (fz/node loc)) (+ i (fz/node loc)) i) (fz/next loc)))))

;Evaluation count : 145260 in 60 samples of 2421 calls.
;Execution time mean : 413.907350 µs
;Execution time std-deviation : 3.643129 µs
;Execution time lower quantile : 410.795751 µs ( 2.5%)
;Execution time upper quantile : 418.456139 µs (97.5%)
;Overhead used : 1.853817 ns
;
;Found 3 outliers in 60 samples (5.0000 %)
;low-severe	 2 (3.3333 %)
;low-mild	 1 (1.6667 %)
;Variance from outliers : 1.6389 % Variance is slightly inflated by outliers