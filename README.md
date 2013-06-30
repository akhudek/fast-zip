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
Case:  :clojure.zip
Evaluation count : 73200 in 60 samples of 1220 calls.
             Execution time mean : 853.431270 µs
    Execution time std-deviation : 22.330544 µs
   Execution time lower quantile : 821.376230 µs ( 2.5%)
   Execution time upper quantile : 907.143627 µs (97.5%)

Found 2 outliers in 60 samples (3.3333 %)
	low-severe	 2 (3.3333 %)
 Variance from outliers : 14.1442 % Variance is moderately inflated by outliers

Case:  :fast-zip
Evaluation count : 162180 in 60 samples of 2703 calls.
             Execution time mean : 385.903558 µs
    Execution time std-deviation : 15.064595 µs
   Execution time lower quantile : 367.073622 µs ( 2.5%)
   Execution time upper quantile : 421.526082 µs (97.5%)

Found 3 outliers in 60 samples (5.0000 %)
	low-severe	 1 (1.6667 %)
	low-mild	 2 (3.3333 %)
 Variance from outliers : 25.4515 % Variance is moderately inflated by outliers
```

I've tested this in a large application that uses zippers in more complicated
ways and got a similar speedup.

## License

Distributed under the Eclipse Public License, the same as Clojure.
