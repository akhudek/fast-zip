(ns fast-zip.core-test
  (:require
    [clojure.test :refer :all]
    [fast-zip.core :as z]))

(def data '[[a * b] + [c * d]])
(def dz (z/vector-zip data))

(deftest vector-zipper-tests
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
          '*)))

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
