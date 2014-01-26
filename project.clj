(defproject fast-zip "0.4.0"
  :description "A modification of clojure.zip that uses protocols and records."
  :url "https://github.com/akhudek/fast-zip"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]]

  :plugins [[perforate "0.3.2"]]

  ;; Prevent lein from disabling jvm optimizations.
  :jvm-opts ^:replace ["-server"]

  :profiles {:dev
             {:dependencies
              [[perforate "0.3.3"]
               [criterium "0.4.3"]]}}

  :perforate {:environments [{:namespaces [fast-zip.core-bench]}]}
  :scm {:name "git"
        :url  "https://github.com/akhudek/fast-zip"}
  :deploy-repositories
  [["clojars" {:signing {:gpg-key "D8B883CA"}}]])
