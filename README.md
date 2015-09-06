# fast-zip

A modified version of [clojure.zip](http://clojuredocs.org/clojure.zip) that
uses `deftype` internally and is written in CLJX format.

## Usage

For Clojure 1.7 use:

```clojure
[fast-zip "0.7.0"]
```

For previous versions use:

```clojure
[fast-zip "0.6.1"]
```

This is a drop in replacement for clojure.zip. Simply require `fast-zip.core`
instead of `clojure.zip`.

## Benchmark

Since 0.6.1 a benchmark run consists of two analysis goals:

1. "walk" traverses a vector zip of three levels of ints and sums the numbers (reduction over leaves)
2. "edit" same setup as for "walk", but actually edits each leaf `(* n 10)` and applies all changes at the end

The current snapshot version of this lib is roughly twice as fast as the 0.5.2 release...

[Historical benchmarks for each version](BENCHMARKS.md)

### Clojure

To run, use `lein perforate`.

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

### ClojureScript

For the clojurescript version, make sure you have node.js installed
along with the [benchmark](https://www.npmjs.com/package/benchmark)
NPM module. Then use `lein clean-bench`

    Benchmark: vector zip walk...
    :clojure.zip.walk x 69.52 ops/sec ±3.58% (67 runs sampled)
    :fast-zip.walk x 394 ops/sec ±1.88% (89 runs sampled)
    Fastest is :fast-zip
    Benchmark: vector zip edit...
    :clojure.zip.edit x 33.89 ops/sec ±2.66% (61 runs sampled)
    :fast-zip.edit x 89.38 ops/sec ±2.13% (78 runs sampled)
    Fastest is :fast-zip.edit

## TODO

* Create benchmarks that represent more functionality and different use
  cases (in progress)

## Contributors

Thanks to

* Brandon Bloom for his advice.
* Zach Tellman
* Joel Holdbrooks for the ClojureScript version.
* David Thomas Hume
* Karsten Schmidt (CLJX restructure, cljc restructure, deftype impl & benchmark updates)

## License

Distributed under the Eclipse Public License, the same as Clojure.
