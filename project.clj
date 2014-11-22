(defproject fast-zip "0.5.2"
  :description "A modification of clojure.zip that uses protocols and records."
  :url "https://github.com/akhudek/fast-zip"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0" :scope "provided"]
                 [org.clojure/clojurescript "0.0-2311" :scope "provided"]]

  :plugins [[perforate "0.3.2"]
            [lein-cljsbuild "1.0.3"]
            [com.cemerick/clojurescript.test "0.3.1"]]

  :source-paths
  ["src/clj" "src/cljs"]

  :test-paths
  ["test/clj" "test/cljs"]

  ;; Prevent lein from disabling jvm optimizations.
  :jvm-opts ^:replace ["-server"]

  :profiles {:dev
             {:dependencies
              [[perforate "0.3.3"]
               [criterium "0.4.3"]
               [org.bodil/cljs-noderepl "0.1.11"]
               [com.cemerick/piggieback "0.1.3"]]
              :node-dependencies
              [benchmark "1.0.0"]
              :plugins
              [[lein-npm "0.4.0"]
               [com.cemerick/austin "0.1.5"]]
              :aliases
              {"clean-test" ~(clojure.string/split
                              "do test, cljsbuild clean, cljsbuild test"
                              #" ")
               "clean-bench" ~(clojure.string/split
                               "do cljsbuild clean, cljsbuild once bench"
                               #" ")}
              :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}

  :perforate {:environments [{:namespaces [fast-zip.core-bench]}]}

  :scm {:name "git"
        :url  "https://github.com/akhudek/fast-zip"}

  :deploy-repositories
  [["clojars" {:signing {:gpg-key "D8B883CA"}}]]

  :cljsbuild
  {:builds [{:id "test"
             :source-paths ["src/cljs" "test/cljs"]
             :compiler {:output-to "target/cljs/testable.js"
                        :optimizations :whitespace
                        :pretty-print true}}
            {:id "bench"
             :source-paths ["src/cljs" "benchmarks"]
             :notify-command ["node" "target/cljs/benchmark.js"]
             :compiler {:target :nodejs
                        :output-to "target/cljs/benchmark.js"
                        :optimizations :simple
                        :pretty-print true}}]
   :test-commands {"unit-tests" ["phantomjs" :runner
                                 "target/cljs/testable.js"]}})
