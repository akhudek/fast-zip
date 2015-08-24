# Release History

## 0.7.0

* Replace CLJX sources with CLJC (reader conditionals)
* Update CLJ & CLJS dependencies (1.7.x)
* Remove Piggieback & Austin dev dependencies

## 0.6.1

* Add ZipperOps deftype to minimize copying effort for new ZipperLocation instances

## 0.6.0

* Switch CLJ version to `deftype` implementation for further 2x speedup
* Refactor project as CLJX setup (to avoid/minimize code duplication)
* Update benchmark setup for both CLJ & CLJS, add benchmarks

## 0.5.2

* Bug fix for leftmost.

## 0.5.1

* Avoid redundant calls to up internally.

## 0.5.0

* ClojureScript support thanks to Joel Holdbrooks.

## 0.4.0

* Bug fixes for empty seq-zip cases.
* Changed internal vector to list for performance increase.
* Improved test suite.

## 0.3.0

* Initial version.
