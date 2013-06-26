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