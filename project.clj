(defproject fast-zip "0.6.1"
  :description "A modification of clojure.zip that uses protocols and records."
  :url "https://github.com/akhudek/fast-zip"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0" :scope "provided"]
                 [org.clojure/clojurescript "0.0-2657" :scope "provided"]]

  :source-paths
  ["src/cljx"]

  :test-paths
  ["target/test-classes"]

  ;; Prevent lein from disabling jvm optimizations.
  :jvm-opts ^:replace ["-server"]

  :profiles {:dev
             {:dependencies
              [[perforate "0.3.4"]
               [criterium "0.4.3"]
               [org.bodil/cljs-noderepl "0.1.11"]
               [com.cemerick/piggieback "0.1.3"]]
              :plugins
              [[perforate "0.3.4"]
               [lein-npm "0.4.0"]
               [lein-cljsbuild "1.0.4"]
               [com.keminglabs/cljx "0.5.0"]
               [com.cemerick/clojurescript.test "0.3.3"]
               [com.cemerick/austin "0.1.5"]]
              :node-dependencies
              [benchmark "1.0.0"]
              :auto-clean false
              :prep-tasks [["cljx" "once"]]
              :aliases
              {"clean-test" ~(clojure.string/split
                              "do clean, cljx once, test, cljsbuild test"
                              #" ")
               "clean-bench" ~(clojure.string/split
                               "do clean, cljx once, cljsbuild once bench"
                               #" ")}
              :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}

  :perforate {:environments [{:namespaces [fast-zip.core-bench]}]}

  :scm {:name "git"
        :url  "https://github.com/akhudek/fast-zip"}

  :deploy-repositories
  [["clojars" {:signing {:gpg-key "D8B883CA"}}]]

  :cljx
  {:builds [{:source-paths ["src/cljx"]
             :output-path "target/classes"
             :rules :clj}
            {:source-paths ["src/cljx"]
             :output-path "target/classes"
             :rules :cljs}
            {:source-paths ["test/cljx"]
             :output-path "target/test-classes"
             :rules :clj}
            {:source-paths ["test/cljx"]
             :output-path "target/test-classes"
             :rules :cljs}]}

  :cljsbuild
  {:builds [{:id "test"
             :source-paths ["target/classes" "target/test-classes"]
             :compiler {:output-to "target/cljs/testable.js"
                        :optimizations :whitespace
                        :pretty-print true}}
            {:id "bench"
             :source-paths ["target/classes" "benchmarks"]
             :notify-command ["node" "target/cljs/benchmark.js"]
             :compiler {:target :nodejs
                        :output-to "target/cljs/benchmark.js"
                        :optimizations :simple
                        :pretty-print true}}]
   :test-commands {"unit-tests" ["phantomjs" :runner
                                 "target/cljs/testable.js"]}})
