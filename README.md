# fast-zip

A modified version of clojure.zip that uses records and protocols internally.

## Usage

```clojure
[fast-zip "0.3.0"]
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
Evaluation count : 75480 in 60 samples of 1258 calls.
             Execution time mean : 805.666773 µs
    Execution time std-deviation : 4.815877 µs
   Execution time lower quantile : 797.942766 µs ( 2.5%)
   Execution time upper quantile : 816.578299 µs (97.5%)

Found 2 outliers in 60 samples (3.3333 %)
	low-severe	 2 (3.3333 %)
 Variance from outliers : 1.6389 % Variance is slightly inflated by outliers

Case:  :fast-zip
Evaluation count : 297900 in 60 samples of 4965 calls.
             Execution time mean : 202.892179 µs
    Execution time std-deviation : 848.456881 ns
   Execution time lower quantile : 201.212286 µs ( 2.5%)
   Execution time upper quantile : 204.187311 µs (97.5%)

Found 1 outliers in 60 samples (1.6667 %)
	low-severe	 1 (1.6667 %)
 Variance from outliers : 1.6389 % Variance is slightly inflated by outliers
```

I've tested this in a large application that uses zippers in more complicated
ways and got a similar speedup.

## Thanks

Thanks to

* Brandon Bloom for his advice.
* Zack Tellman

## License

Distributed under the Eclipse Public License, the same as Clojure.
