# fast-zip

A modified version of clojure.zip that uses records internally.

## Usage

```clojure
[fast-zip "0.6.0-SNAPSHOT"]
```

This is a drop in replacement for clojure.zip. Simply require ``fast-zip.core``
instead of ``clojure.zip``.

## Benchmark

The below traverses a vector zip with three levels and sums the numbers.

### Clojure

To run, use ``lein perforate``.

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

### ClojureScript

For the clojurescript version, make sure you have node.js installed along
with the ``benchmark`` module. Then use ``lein clean-bench``

    :clojure.zip x 114 ops/sec ±0.64% (85 runs sampled)
    :fast-zip x 197 ops/sec ±0.62% (88 runs sampled)
    Fastest is :fast-zip

## TODO

* Create benchmarks that represent more functionality and different use
  cases.

## Contributors

Thanks to

* Brandon Bloom for his advice.
* Zach Tellman
* Joel Holdbrooks for the ClojureScript version.
* David Thomas Hume

## License

Distributed under the Eclipse Public License, the same as Clojure.
