# fast-zip

A modified version of clojure.zip that uses records and protocols internally.

## Usage

```clojure
[fast-zip "0.1.0"]
```

This is a drop in replacement for clojure.zip. Simply require ``fast-zip.core``
instead of ``clojure.zip``.

## Benchmark

The below  traverses a vector zip with three levels and sums the numbers. To
run, use ``lein perforate``.

```
Goal:  Benchmark vector zip.
-----
Case:  :fast-zip
Evaluation count : 57900 in 60 samples of 965 calls.
             Execution time mean : 1.061294 ms
    Execution time std-deviation : 18.324036 µs
   Execution time lower quantile : 1.040463 ms ( 2.5%)
   Execution time upper quantile : 1.101971 ms (97.5%)

Found 6 outliers in 60 samples (10.0000 %)
	low-severe	 5 (8.3333 %)
	low-mild	 1 (1.6667 %)
 Variance from outliers : 6.2784 % Variance is slightly inflated by outliers

Case:  :clojure.zip
Evaluation count : 22140 in 60 samples of 369 calls.
             Execution time mean : 2.742761 ms
    Execution time std-deviation : 58.216480 µs
   Execution time lower quantile : 2.667016 ms ( 2.5%)
   Execution time upper quantile : 2.867317 ms (97.5%)

Found 1 outliers in 60 samples (1.6667 %)
	low-severe	 1 (1.6667 %)
 Variance from outliers : 9.4220 % Variance is slightly inflated by outliers
```

I've tested this in a large application that uses zippers in more complicated
ways and got a similar speedup.

## License

Distributed under the Eclipse Public License, the same as Clojure.
