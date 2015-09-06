(defproject fast-zip "0.7.0"
  :description "A modification of clojure.zip that uses protocols and records."
  :url "https://github.com/akhudek/fast-zip"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0" :scope "provided"]
                 [org.clojure/clojurescript "1.7.107" :scope "provided"]]

  ;; Prevent lein from disabling jvm optimizations.
  :jvm-opts ^:replace []

  :profiles {:dev
             {:dependencies
              [[perforate "0.3.4"]
               [criterium "0.4.3"]]
              :plugins
              [[perforate "0.3.4"]
               [lein-npm "0.6.1"]
               [lein-cljsbuild "1.0.6"]
               [com.cemerick/clojurescript.test "0.3.3"]]
              :node-dependencies
              [benchmark "1.0.0"]
              :aliases
              {"clean-test" ~(clojure.string/split
                              "do clean, test, cljsbuild test"
                              #" ")
               "clean-bench" ~(clojure.string/split
                               "do clean, cljsbuild once bench"
                               #" ")}}}

  :perforate {:environments [{:namespaces [fast-zip.core-bench]}]}

  :scm {:name "git"
        :url  "https://github.com/akhudek/fast-zip"}

  :deploy-repositories
  [["clojars" {:signing {:gpg-key "D8B883CA"}}]]

  :cljsbuild
  {:builds [{:id "test"
             :source-paths ["src" "test"]
             :compiler {:output-to "target/cljs/testable.js"
                        :optimizations :whitespace
                        :pretty-print true}}
            {:id "bench"
             :source-paths ["src" "benchmarks"]
             :notify-command ["node" "target/cljs/benchmark.js"]
             :compiler {:target :nodejs
                        :output-to "target/cljs/benchmark.js"
                        :optimizations :simple
                        :pretty-print true}}]
   :test-commands {"unit-tests" ["phantomjs" :runner
                                 "target/cljs/testable.js"]}})
