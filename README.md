# fast-zip

A modified version of clojure.zip that uses records and protocols internally.

## Usage

```clojure
[fast-zip "0.2.0"]
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
Evaluation count : 74820 in 60 samples of 1247 calls.
             Execution time mean : 811.315704 µs
    Execution time std-deviation : 20.717347 µs
   Execution time lower quantile : 792.166800 µs ( 2.5%)
   Execution time upper quantile : 871.133119 µs (97.5%)

Found 6 outliers in 60 samples (10.0000 %)
	low-severe	 2 (3.3333 %)
	low-mild	 4 (6.6667 %)
 Variance from outliers : 12.6233 % Variance is moderately inflated by outliers

Case:  :fast-zip
Evaluation count : 166320 in 60 samples of 2772 calls.
             Execution time mean : 361.219799 µs
    Execution time std-deviation : 5.828158 µs
   Execution time lower quantile : 355.780853 µs ( 2.5%)
   Execution time upper quantile : 373.429293 µs (97.5%)

Found 3 outliers in 60 samples (5.0000 %)
	low-severe	 1 (1.6667 %)
	low-mild	 2 (3.3333 %)
 Variance from outliers : 1.6389 % Variance is slightly inflated by outliers```

I've tested this in a large application that uses zippers in more complicated
ways and got a similar speedup.

## Thanks

Thanks to Brandon Bloom for his help.

## License

Distributed under the Eclipse Public License, the same as Clojure.
