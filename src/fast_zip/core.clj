;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

;functional hierarchical zipper, with navigation, editing and enumeration
;see Huet

(ns ^{:doc "Functional hierarchical zipper, with navigation, editing,
  and enumeration.  See Huet"
      :author "Rich Hickey, modified by Alexander K. Hudek"}
  fast-zip.core
  (:refer-clojure :exclude (replace remove next)))

(defrecord ZipperPath [l r ppath pnodes changed?])

(defprotocol IZipper
  (zip-branch? [this node] "Return true if can have children, even if it currently doesn't.")
  (zip-children [this node] "Return a seq of it's children.")
  (zip-make-node [this node children] "Given a seq of children, return a new branch node with the supplied children."))

(defprotocol IZipperLocation
  (node [loc] "Returns the node at loc")
  (branch? [loc] "Returns true if the node at loc is a branch")
  (children [loc] "Returns a seq of the children of node at loc, which must be a branch")
  (make-node [loc node children] "Returns a new branch node, given an existing node and new children. The loc is only used to supply the constructor.")
  (path [loc] "Returns a seq of nodes leading to this loc")
  (lefts [loc]  "Returns a seq of the left siblings of this loc")
  (rights [loc] "Returns a seq of the right siblings of this loc")
  (down [loc] "Returns the loc of the leftmost child of the node at this loc, or nil if no children")
  (up [loc] "Returns the loc of the parent of the node at this loc, or nil if at the top")
  (root [loc] "zips all the way up and returns the root node, reflecting any changes.")
  (right [loc] "Returns the loc of the right sibling of the node at this loc, or nil")
  (rightmost [loc] "Returns the loc of the rightmost sibling of the node at this loc, or self")
  (left [loc] "Returns the loc of the left sibling of the node at this loc, or nil")
  (leftmost [loc] "Returns the loc of the leftmost sibling of the node at this loc, or self")
  (insert-left [loc item] "Inserts the item as the left sibling of the node at this loc, without moving")
  (insert-right [loc item] "Inserts the item as the right sibling of the node at this loc, without moving")
  (replace [loc node] "Replaces the node at this loc, without moving")
  (insert-child [loc item] "Inserts the item as the leftmost child of the node at this loc, without moving")
  (append-child [loc item] "Inserts the item as the rightmost child of the node at this loc, without moving")
  (next [loc]
    "Moves to the next loc in the hierarchy, depth-first. When reaching
  the end, returns a distinguished loc detectable via end?. If already
  at the end, stays there.")
  (prev [loc] "Moves to the previous loc in the hierarchy, depth-first. If already at the root, returns nil.")
  (end? [loc] "Returns true if loc represents the end of a depth-first walk")
  (remove [loc] "Removes the node at loc, returning the loc that would have preceded it in a depth-first walk."))

(defrecord ZipperLocation [zip node path]
  IZipperLocation
  (node [_] node)

  (branch? [loc] (zip-branch? zip node))

  (children [loc] (zip-children zip node))

  (make-node [loc node children] (zip-make-node zip node children))

  (path [loc] (:pnodes path))

  (lefts [loc] (seq (:l path)))

  (rights [loc] (:r path))

  (down [loc]
    (when (branch? loc)
      (when-let [cs (children loc)]
        (ZipperLocation.
          zip
          (first cs)
          (ZipperPath. [] (clojure.core/next cs) path (if path (conj (:pnodes path) node) [node]) nil)))))

  (up [loc]
    (when-let [pnodes (:pnodes path)]
      (let [pnode (peek pnodes)]
        (if (:changed? path)
          (ZipperLocation. zip
            (make-node loc pnode (concat (:l path) (cons node (:r path))))
            (if-let [ppath (:ppath path)] (assoc ppath :changed? true)))
          (ZipperLocation. zip pnode (:ppath path))))))

  (root [loc]
    (loop [loc loc]
      (if (= :end (:path loc))
        (:node loc)
        (let [p (up loc)]
          (if p
            (recur p)
            (:node loc))))))

  (right [loc]
    (when-let [r (and path (:r path))]
      (ZipperLocation. zip (first r) (assoc path :l (conj (:l path) node) :r (clojure.core/next r)))))

  (rightmost [loc]
    (if-let [r (and path (:r path))]
      (ZipperLocation. zip (last r) (assoc path :l (apply conj (:l path) node (butlast r)) :r nil))
      loc))

  (left [loc]
    (when (and path (seq (:l path)))
      (ZipperLocation. zip (peek (:l path)) (assoc path :l (pop (:l path)) :r (cons node (:r path))))))

  (leftmost [loc]
    (if (and path (seq (:l path)))
      (ZipperLocation. zip (first (:l path)) (assoc path :l [] :r (concat (rest (:l path)) [node] (:r path))))
      loc))

  (insert-left [loc item]
    (if (nil? path)
      (throw (new Exception "Insert at top"))
      (ZipperLocation. zip node (assoc path :l (conj (:l path) item) :changed? true))))

  (insert-right [loc item]
    (if (nil? path)
      (throw (new Exception "Insert at top"))
      (ZipperLocation. zip node (assoc path :r (cons item (:r path)) :changed? true))))

  (replace [loc node]
    (ZipperLocation. zip node (assoc path :changed? true)))

  (insert-child [loc item]
    (replace loc (make-node loc node (cons item (children loc)))))

  (append-child [loc item]
    (replace loc (make-node loc node (concat (children loc) [item]))))

  (next [loc]
    (if (= :end path)
      loc
      (or
        (and (branch? loc) (down loc))
        (right loc)
        (loop [p loc]
          (if (up p)
            (or (right (up p)) (recur (up p)))
            (ZipperLocation. zip (:node p) :end))))))

  (prev [loc]
    (if-let [lloc (left loc)]
      (loop [loc lloc]
        (if-let [child (and (branch? loc) (down loc))]
          (recur (rightmost child))
          loc))
      (up loc)))

  (end? [loc] (= :end path))

  (remove [loc]
    (if (nil? path)
      (throw (new Exception "Remove at top"))
      (if (pos? (count (:l path)))
        (loop [loc (ZipperLocation. zip (peek (:l path)) (assoc path :l (pop (:l path)) :changed? true))]
          (if-let [child (and (branch? loc) (down loc))]
            (recur (rightmost child))
            loc))
        (ZipperLocation. zip
          (make-node loc (peek (:pnodes path)) (:r path))
          (if-let [ppath (:ppath path)] (and ppath (assoc ppath :changed? true))))))))

(defn edit
  "Replaces the node at this loc with the value of (f node args)"
  [loc f & args]
  (replace loc (apply f (:node loc) args)))


(defn zipper
  "Creates a new zipper structure. 

  branch? is a fn that, given a node, returns true if can have
  children, even if it currently doesn't.

  children is a fn that, given a branch node, returns a seq of its
  children.

  make-node is a fn that, given an existing node and a seq of
  children, returns a new branch node with the supplied children.
  root is the root node."
  {:added "1.0"}
  [branch? children make-node root]
  (ZipperLocation.
    (reify IZipper
      (zip-branch? [_ node] (branch? node))
      (zip-children [_ node] (children node))
      (zip-make-node [_ node children] (make-node node children)))
    root
    nil))

(defn seq-zip
  "Returns a zipper for nested sequences, given a root sequence"
  {:added "1.0"}
  [root]
  (zipper seq?
    identity
    (fn [node children] (with-meta children (meta node)))
    root))

(defn vector-zip
  "Returns a zipper for nested vectors, given a root vector"
  {:added "1.0"}
  [root]
  (zipper vector?
    seq
    (fn [node children] (with-meta (vec children) (meta node)))
    root))

(defn xml-zip
  "Returns a zipper for xml elements (as from xml/parse),
  given a root element"
  {:added "1.0"}
  [root]
  (zipper (complement string?)
    (comp seq :content)
    (fn [node children]
      (assoc node :content (and children (apply vector children))))
    root))

(comment

  (load-file "/Users/rich/dev/clojure/src/zip.clj")
  (refer 'zip)
  (def data '[[a * b] + [c * d]])
  (def dz (vector-zip data))

  (right (down (right (right (down dz)))))
  (lefts (right (down (right (right (down dz))))))
  (rights (right (down (right (right (down dz))))))
  (up (up (right (down (right (right (down dz)))))))
  (path (right (down (right (right (down dz))))))

  (-> dz down right right down right)
  (-> dz down right right down right (replace '/) root)
  (-> dz next next (edit str) next next next (replace '/) root)
  (-> dz next next next next next next next next next remove root)
  (-> dz next next next next next next next next next remove (insert-right 'e) root)
  (-> dz next next next next next next next next next remove up (append-child 'e) root)

  (end? (-> dz next next next next next next next next next remove next))

  (-> dz next remove next remove root)

  (loop [loc dz]
    (if (end? loc)
      (root loc)
      (recur (next (if (= '* (node loc))
                     (replace loc '/)
                     loc)))))

  (loop [loc dz]
    (if (end? loc)
      (root loc)
      (recur (next (if (= '* (node loc))
                     (remove loc)
                     loc)))))
  )