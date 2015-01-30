# Benchmark History

## 0.6.0-SNAPSHOT

Added new benchmarks for node editing. Existing/previous benchmark goal now named as "vector zip walk"...

Run on a MBP2010 with a 2.66ghz core i7, osx 10.8.5 and java 1.8.0_25-b17.

### ClojureScript

    Benchmark: vector zip walk...
    :clojure.zip.walk x 69.52 ops/sec ±3.58% (67 runs sampled)
    :fast-zip.walk x 394 ops/sec ±1.88% (89 runs sampled)
    Fastest is :fast-zip
    Benchmark: vector zip edit...
    :clojure.zip.edit x 33.89 ops/sec ±2.66% (61 runs sampled)
    :fast-zip.edit x 89.38 ops/sec ±2.13% (78 runs sampled)
    Fastest is :fast-zip.edit

### Clojure

    WARNING: Final GC required 2.904199000536039 % of runtime
    Goal:  Benchmark vector zip edit.
    -----
    Case:  :fast-zip
    Evaluation count : 96600 in 60 samples of 1610 calls.
                 Execution time mean : 613.696756 µs
        Execution time std-deviation : 23.370402 µs
       Execution time lower quantile : 588.826153 µs ( 2.5%)
       Execution time upper quantile : 687.558243 µs (97.5%)
                       Overhead used : 2.997006 ns

    Found 3 outliers in 60 samples (5.0000 %)
    	low-severe	 3 (5.0000 %)
     Variance from outliers : 23.8777 % Variance is moderately inflated by outliers

    Case:  :clojure.zip
    Evaluation count : 27420 in 60 samples of 457 calls.
                 Execution time mean : 2.143100 ms
        Execution time std-deviation : 55.341182 µs
       Execution time lower quantile : 2.046640 ms ( 2.5%)
       Execution time upper quantile : 2.248948 ms (97.5%)
                       Overhead used : 2.997006 ns

    Goal:  Benchmark vector zip walk.
    -----
    Case:  :fast-zip
    Evaluation count : 578700 in 60 samples of 9645 calls.
                 Execution time mean : 106.602340 µs
        Execution time std-deviation : 3.705304 µs
       Execution time lower quantile : 101.726377 µs ( 2.5%)
       Execution time upper quantile : 117.508157 µs (97.5%)
                       Overhead used : 2.997006 ns

    Found 5 outliers in 60 samples (8.3333 %)
    	low-severe	 2 (3.3333 %)
    	low-mild	 3 (5.0000 %)
     Variance from outliers : 20.6548 % Variance is moderately inflated by outliers

    Case:  :clojure.zip
    Evaluation count : 46020 in 60 samples of 767 calls.
                 Execution time mean : 1.326133 ms
        Execution time std-deviation : 32.156029 µs
       Execution time lower quantile : 1.262908 ms ( 2.5%)
       Execution time upper quantile : 1.387965 ms (97.5%)
                       Overhead used : 2.997006 ns

    Found 1 outliers in 60 samples (1.6667 %)
    	low-severe	 1 (1.6667 %)
     Variance from outliers : 12.5459 % Variance is moderately inflated by outliers

## 0.5.1

Run on a mac mini with a 2.6ghz core i7, osx 10.9.5, node.js 0.10.31,
and java 1.7.0_51. ClojureScript uses simple optimization.

### ClojureScript

    :clojure.zip x 114 ops/sec ±0.64% (85 runs sampled)
    :fast-zip x 197 ops/sec ±0.62% (88 runs sampled)
    Fastest is :fast-zip

### Clojure

    ======================
    WARNING: Final GC required 1.352547229118579 % of runtime
    Goal:  Benchmark vector zip.
    -----
    Case:  :clojure.zip
    Evaluation count : 58260 in 60 samples of 971 calls.
                 Execution time mean : 1.049589 ms
        Execution time std-deviation : 17.962641 µs
       Execution time lower quantile : 1.018927 ms ( 2.5%)
       Execution time upper quantile : 1.082843 ms (97.5%)
                       Overhead used : 1.896478 ns

    Case:  :fast-zip
    Evaluation count : 326400 in 60 samples of 5440 calls.
                 Execution time mean : 185.443364 µs
        Execution time std-deviation : 2.938738 µs
       Execution time lower quantile : 181.131376 µs ( 2.5%)
       Execution time upper quantile : 192.279133 µs (97.5%)
                       Overhead used : 1.896478 ns

    Found 2 outliers in 60 samples (3.3333 %)
        low-severe	 2 (3.3333 %)
     Variance from outliers : 1.6389 % Variance is slightly inflated by outliers

## 0.5.0

Run on a mac mini with a 2.6ghz core i7, osx 10.9.5, node.js 0.10.31,
and java 1.7.0_51. ClojureScript uses simple optimization.

### ClojureScript

    :clojure.zip x 116 ops/sec ±0.65% (83 runs sampled)
    :fast-zip x 194 ops/sec ±0.85% (92 runs sampled)
    Fastest is :fast-zip 

### Clojure

    ======================
    WARNING: Final GC required 1.467359386689346 % of runtime
    Goal:  Benchmark vector zip.
    -----
    Case:  :clojure.zip
    Evaluation count : 60480 in 60 samples of 1008 calls.
                 Execution time mean : 1.002237 ms
        Execution time std-deviation : 7.317531 µs
       Execution time lower quantile : 990.607677 µs ( 2.5%)
       Execution time upper quantile : 1.015757 ms (97.5%)
                       Overhead used : 2.442790 ns
    
    Case:  :fast-zip
    Evaluation count : 316140 in 60 samples of 5269 calls.
                 Execution time mean : 191.258856 µs
        Execution time std-deviation : 2.819942 µs
       Execution time lower quantile : 188.802833 µs ( 2.5%)
       Execution time upper quantile : 198.838310 µs (97.5%)
                       Overhead used : 2.442790 ns
    
    Found 8 outliers in 60 samples (13.3333 %)
    	low-severe	 4 (6.6667 %)
    	low-mild	 4 (6.6667 %)
     Variance from outliers : 1.6389 % Variance is slightly inflated by outliers

## 0.4.0

Run on a mac mini with a 2.6ghz core i7 and osx version 10.9.1.

    ======================
    WARNING: Final GC required 1.450308932831415 % of runtime
    Goal:  Benchmark vector zip.
    -----
    Case:  :fast-zip
    Evaluation count : 246120 in 60 samples of 4102 calls.
                 Execution time mean : 242.777689 µs
        Execution time std-deviation : 1.965914 µs
       Execution time lower quantile : 238.996103 µs ( 2.5%)
       Execution time upper quantile : 247.075613 µs (97.5%)
                       Overhead used : 1.947263 ns

    Found 2 outliers in 60 samples (3.3333 %)
        low-severe	 2 (3.3333 %)
     Variance from outliers : 1.6389 % Variance is slightly inflated by outliers

    Case:  :clojure.zip
    Evaluation count : 62820 in 60 samples of 1047 calls.
                 Execution time mean : 960.326181 µs
        Execution time std-deviation : 4.915537 µs
       Execution time lower quantile : 950.592132 µs ( 2.5%)
       Execution time upper quantile : 969.813479 µs (97.5%)
                       Overhead used : 1.947263 ns

## 0.3.0

    ======================
    WARNING: Final GC required 1.4305224255444802 % of runtime
    Goal:  Benchmark vector zip.
    -----
    Case:  :fast-zip
    Evaluation count : 232020 in 60 samples of 3867 calls.
                 Execution time mean : 264.515173 µs
        Execution time std-deviation : 4.847643 µs
       Execution time lower quantile : 258.657768 µs ( 2.5%)
       Execution time upper quantile : 273.488270 µs (97.5%)
                       Overhead used : 1.916713 ns

    Found 1 outliers in 60 samples (1.6667 %)
        low-severe	 1 (1.6667 %)
     Variance from outliers : 7.7949 % Variance is slightly inflated by outliers

    Case:  :clojure.zip
    Evaluation count : 61800 in 60 samples of 1030 calls.
                 Execution time mean : 972.471093 µs
        Execution time std-deviation : 11.575673 µs
       Execution time lower quantile : 953.602331 µs ( 2.5%)
       Execution time upper quantile : 988.157185 µs (97.5%)
                       Overhead used : 1.916713 ns

    Found 1 outliers in 60 samples (1.6667 %)
        low-severe	 1 (1.6667 %)
     Variance from outliers : 1.6389 % Variance is slightly inflated by outliers
