(ns fast-zip.core-test
  (:require
    #+cljs [cemerick.cljs.test :refer-macros [deftest testing is]]
    #+clj [clojure.test :refer :all]
    [fast-zip.core :as z]))

(def data '[[a * b] + [c * d]])
(def dz (z/vector-zip data))

(deftest zipper-tests
  (testing "Edge cases"
    (let [ez (z/seq-zip [])]
      (is (nil? (z/path ez)))
      (is (nil? (z/lefts ez)))
      (is (nil? (z/rights ez)))
      (is (nil? (z/down ez)))
      (is (nil? (z/right ez)))
      (is (nil? (z/left ez)))
      (is (nil? (z/up ez)))
      (is (= [] (z/node (z/seq-zip []))))
      (is (= '() (z/node (z/seq-zip '()))))))

  (testing "Basic navigation"
    (is (= (z/node (z/right (z/down (z/right (z/right (z/down dz))))))
          '* ))
    (is (= (z/lefts (z/right (z/down (z/right (z/right (z/down dz))))))
          '(c)))
    (is (= (z/rights (z/right (z/down (z/right (z/right (z/down dz))))))
          '(d)))
    (is (= (z/node (z/up (z/up (z/right (z/down (z/right (z/right (z/down dz))))))))
          data))
    (is (= (z/path (z/right (z/down (z/right (z/right (z/down dz))))))
          '[[[a * b] + [c * d]] [c * d]]))
    (is (= (-> dz z/down z/right z/right z/down z/right z/node)
          '*))
    (is (= (-> dz z/down z/rights)
           '(+ [c * d])))
    (is (= (-> dz z/down z/right z/right z/lefts)
           '([a * b] +)))
    (is (= (-> dz z/down z/rightmost z/lefts)
           '([a * b] +)))
    (is (= (-> dz z/down z/right z/right z/leftmost z/rights)
           '(+ [c * d])))
    (is (= (-> (z/seq-zip '(a b c d)) z/down z/right z/right z/leftmost z/node)
           'a)))

  (testing "Edits"
    (is (= (-> dz z/down z/right z/right z/down z/right (z/replace '/) z/root)
          '[[a * b] + [c / d]]))
    (is (= (-> dz z/next z/next (z/edit str) z/next z/next z/next (z/replace '/) z/root)
          '[["a" * b] / [c * d]]))
    (is (= (-> dz z/next z/next z/next z/next z/next z/next z/next z/next z/next z/remove z/root)
          '[[a * b] + [c *]]))
    (is (= (-> dz z/next z/next z/next z/next z/next z/next z/next z/next z/next z/remove (z/insert-right 'e) z/root)
          '[[a * b] + [c * e]]))
    (is (= (-> dz z/next z/next z/next z/next z/next z/next z/next z/next z/next z/remove z/up (z/append-child 'e) z/root)
          '[[a * b] + [c * e]]))
    (is (z/end? (-> dz z/next z/next z/next z/next z/next z/next z/next z/next z/next z/remove z/next)))
    (is (= (-> dz z/next z/remove z/next z/remove z/root)
          '[[c * d]]))
    (is (= '[[a / b] + [c / d]]
          (loop [loc dz]
            (if (z/end? loc)
              (z/root loc)
              (recur (z/next (if (= '* (z/node loc))
                             (z/replace loc '/)
                             loc)))))))
    (is (= '[[a b] + [c d]]
          (loop [loc dz]
            (if (z/end? loc)
              (z/root loc)
              (recur (z/next (if (= '* (z/node loc))
                             (z/remove loc)
                             loc)))))))))
