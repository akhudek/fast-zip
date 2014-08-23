(defproject fast-zip "0.4.0"
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
               [criterium "0.4.3"]]
              :aliases
              {"clean-test" ~(clojure.string/split
                              "do test, cljsbuild clean, cljsbuild test"
                              #" ")}}}

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
                        :pretty-print true}}]
   :test-commands {"unit-tests" ["phantomjs" :runner
                                 "target/cljs/testable.js"]}})
