(defproject fast-zip "0.1.0-SNAPSHOT"
  :description "A modification of clojure.zip that uses protocols and records."
  :url "https://github.com/akhudek/fast-zip"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]]

  :plugins [[perforate "0.3.2"]]

  :profiles {:dev
             {:dependencies
              [[perforate "0.3.2"]
               [criterium "0.4.1"]]}}

  :perforate {:environments [{:namespaces [fast-zip.core-bench]}]}
  )
