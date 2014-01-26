# Benchmark History

Run on a mac mini with a 2.6ghz core i7 and osx version 10.9.1.

## 0.4.0
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