(ns fast-zip.core-test
  (:require #_[clojure.test :refer :all]
            [fast-zip.core :as fz]
            [clojure.zip :as z]
            [criterium.core :refer :all]))

(set! *warn-on-reflection* true)

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))


(def big-vec (vec (repeat 10 (vec (repeat 10 (vec (range 10)))))))

(defn zip-test1
  []
  (loop [i 0, loc (z/vector-zip big-vec)]
    (if (z/end? loc)
      i
      (recur (if (integer? (z/node loc)) (+ i (z/node loc)) i) (z/next loc)))))

(defn zip-test2
  []
  (loop [i 0, loc (fz/vector-zip big-vec)]
    (if (fz/end? loc)
      i
      (recur (if (integer? (fz/node loc)) (+ i (fz/node loc)) i) (fz/next loc)))))

(bench (zip-test1))

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

;Evaluation count : 60060 in 60 samples of 1001 calls.
;Execution time mean : 996.067561 µs
;Execution time std-deviation : 4.652621 µs
;Execution time lower quantile : 987.152892 µs ( 2.5%)
;Execution time upper quantile : 1.006544 ms (97.5%)
;Overhead used : 1.953928 ns
;
;Found 4 outliers in 60 samples (6.6667 %)
;low-severe	 4 (6.6667 %)
;Variance from outliers : 1.6389 % Variance is slightly inflated by outliers

(bench (zip-test2))

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

;Evaluation count : 140820 in 60 samples of 2347 calls.
;Execution time mean : 427.145554 µs
;Execution time std-deviation : 3.563011 µs
;Execution time lower quantile : 421.159529 µs ( 2.5%)
;Execution time upper quantile : 433.263055 µs (97.5%)
;Overhead used : 1.953928 ns
;
;Found 3 outliers in 60 samples (5.0000 %)
;low-severe	 1 (1.6667 %)
;low-mild	 1 (1.6667 %)
;high-mild	 1 (1.6667 %)
;Variance from outliers : 1.6389 % Variance is slightly inflated by outliers

;Evaluation count : 140880 in 60 samples of 2348 calls.
;Execution time mean : 428.739394 µs
;Execution time std-deviation : 4.358831 µs
;Execution time lower quantile : 423.926922 µs ( 2.5%)
;Execution time upper quantile : 438.776911 µs (97.5%)
;Overhead used : 1.953928 ns
;
;Found 7 outliers in 60 samples (11.6667 %)
;low-severe	 4 (6.6667 %)
;low-mild	 3 (5.0000 %)
;Variance from outliers : 1.6389 % Variance is slightly inflated by outliers

;Evaluation count : 154860 in 60 samples of 2581 calls.
;Execution time mean : 390.840536 µs
;Execution time std-deviation : 4.201021 µs
;Execution time lower quantile : 384.734195 µs ( 2.5%)
;Execution time upper quantile : 402.615985 µs (97.5%)
;Overhead used : 1.953928 ns
;
;Found 5 outliers in 60 samples (8.3333 %)
;low-severe	 3 (5.0000 %)
;low-mild	 2 (3.3333 %)
;Variance from outliers : 1.6389 % Variance is slightly inflated by outliers