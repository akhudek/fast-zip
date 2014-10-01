# fast-zip

A modified version of clojure.zip that uses records internally.

## Usage

```clojure
[fast-zip "0.5.0"]
```

This is a drop in replacement for clojure.zip. Simply require ``fast-zip.core``
instead of ``clojure.zip``.

## Benchmark

The below  traverses a vector zip with three levels and sums the numbers. To
run, use ``lein perforate``.

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

For the clojurescript version, make sure you have node.js installed along
with the ``benchmark`` module. Then use ``lein clean-bench``

## TODO

* Create benchmarks that represent more functionality and different use
  cases.

## Thanks

Thanks to

* Brandon Bloom for his advice.
* Zach Tellman
* Joel Holdbrooks for the ClojureScript version.

## License

Distributed under the Eclipse Public License, the same as Clojure.
