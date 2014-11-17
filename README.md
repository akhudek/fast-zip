# fast-zip

A modified version of clojure.zip that uses records internally.

## Usage

```clojure
[fast-zip "0.5.1"]
```

This is a drop in replacement for clojure.zip. Simply require ``fast-zip.core``
instead of ``clojure.zip``.

## Benchmark

The below  traverses a vector zip with three levels and sums the numbers.

### Clojure

To run, use ``lein perforate``.

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
