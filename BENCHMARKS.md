# Benchmark History

## 0.6.0-SNAPSHOT

Run on a MBP2010 with a 2.66ghz core i7, osx 10.8.5 and java 1.8.0_25-b17.

### Clojure

FYI 0.5.2 mean stats on this machine: clj.zip: 1.178178 ms, fast-zip: 224.407510 µs
Switch to `deftype` vs. `defrecord` caused 2x improvement!

    ======================
    WARNING: Final GC required 2.8625100996663813 % of runtime
    Goal:  Benchmark vector zip.
    -----
    Case:  :clojure.zip
    Evaluation count : 45840 in 60 samples of 764 calls.
                 Execution time mean : 1.333396 ms
        Execution time std-deviation : 24.504126 µs
       Execution time lower quantile : 1.279000 ms ( 2.5%)
       Execution time upper quantile : 1.374372 ms (97.5%)
                       Overhead used : 2.613253 ns

    Found 1 outliers in 60 samples (1.6667 %)
    	low-severe	 1 (1.6667 %)
     Variance from outliers : 7.7979 % Variance is slightly inflated by outliers

    Case:  :fast-zip
    Evaluation count : 564900 in 60 samples of 9415 calls.
                 Execution time mean : 107.774774 µs
        Execution time std-deviation : 1.799526 µs
       Execution time lower quantile : 104.269327 µs ( 2.5%)
       Execution time upper quantile : 111.025326 µs (97.5%)
                       Overhead used : 2.613253 ns

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
