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

(set! *warn-on-reflection* true)

(defrecord ZipperPath [l r ppath pnodes changed?])

(defprotocol IZipper
  (zip-branch? [this node] "Return true if can have children, even if it currently doesn't.")
  (zip-children [this node] "Return a seq of it's children.")
  (zip-make-node [this node children] "Given a seq of children, return a new branch node with the supplied children."))

(defrecord ZipperLocation [zip node path])

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

(defn node
  "Returns the node at loc"
  [^ZipperLocation loc]
  (.node loc))

(defn branch?
  "Returns true if the node at loc is a branch"
  [^ZipperLocation loc]
  (zip-branch? (.zip loc) (.node loc)))

(defn children
  "Returns a seq of the children of node at loc, which must be a branch"
  [^ZipperLocation loc]
  (zip-children (.zip loc) (.node loc)))

(defn make-node
  "Returns a new branch node, given an existing node and new children.
  The loc is only used to supply the constructor."
  [^ZipperLocation loc node children]
  (zip-make-node (.zip loc) node children))

(defn path
  "Returns a seq of nodes leading to this loc"
  [^ZipperLocation loc]
  (.pnodes ^ZipperPath (.path loc)))

(defn lefts
  "Returns a seq of the left siblings of this loc"
  [^ZipperLocation loc]
  (seq (.l ^ZipperPath (.path loc))))

(defn rights
  "Returns a seq of the right siblings of this loc"
  [^ZipperLocation loc]
  (.r ^ZipperPath (.path loc)))

(defn down
  "Returns the loc of the leftmost child of the node at this loc,
  or nil if no children"
  [^ZipperLocation loc]
  (when (branch? loc)
    (when-let [cs (children loc)]
      (let [node (.node loc), path ^ZipperPath (.path loc)]
        (ZipperLocation.
          (.zip loc)
          (first cs)
          (ZipperPath. [] (clojure.core/next cs) path (if path (conj (.pnodes path) node) [node]) nil))))))

(defn up
  "Returns the loc of the parent of the node at this loc, or nil if at the top"
  [^ZipperLocation loc]
  (let [node (.node loc), path ^ZipperPath (.path loc)]
    (when-let [pnodes (and path (.pnodes path))]
      (let [pnode (peek pnodes)]
        (if (.changed? path)
          (ZipperLocation.
            (.zip loc)
            (make-node loc pnode (concat (.l path) (cons node (.r path))))
            (if-let [ppath (.ppath path)] (assoc ppath :changed? true)))
          (ZipperLocation. (.zip loc) pnode (.ppath path)))))))

(defn root
  "zips all the way up and returns the root node, reflecting any changes."
  [^ZipperLocation loc]
  (if (= :end (.path loc))
    (.node loc)
    (let [p (up loc)]
      (if p
        (recur p)
        (.node loc)))))

(defn right
  "Returns the loc of the right sibling of the node at this loc, or nil"
  [^ZipperLocation loc]
  (let [path ^ZipperPath (.path loc)]
    (when-let [r (and path (.r path))]
      (ZipperLocation. (.zip loc) (first r) (assoc path :l (conj (.l path) (.node loc)) :r (clojure.core/next r))))))

(defn rightmost
  "Returns the loc of the rightmost sibling of the node at this loc, or self"
  [^ZipperLocation loc]
  (let [path ^ZipperPath (.path loc)]
    (if-let [r (and path (.r path))]
      (ZipperLocation. (.zip loc) (last r) (assoc path :l (apply conj (.l path) (.node loc) (butlast r)) :r nil))
      loc)))

(defn left
  "Returns the loc of the left sibling of the node at this loc, or nil"
  [^ZipperLocation loc]
  (let [path ^ZipperPath (.path loc)]
    (when (and path (seq (.l path)))
      (ZipperLocation. (.zip loc) (peek (.l path)) (assoc path :l (pop (.l path)) :r (cons (.node loc) (.r path)))))))

(defn leftmost
  "Returns the loc of the leftmost sibling of the node at this loc, or self"
  [^ZipperLocation loc]
  (let [path ^ZipperPath (.path loc)]
    (if (and path (seq (.l path)))
      (ZipperLocation. (.zip loc) (first (.l path)) (assoc path :l [] :r (concat (rest (.l path)) [(.node loc)] (.r path))))
      loc)))

(defn insert-left
  "Inserts the item as the left sibling of the node at this loc, without moving"
  [^ZipperLocation loc item]
  (if-let [path ^ZipperPath (.path loc)]
    (ZipperLocation. (.zip loc) (.node loc) (assoc path :l (conj (.l path) item) :changed? true))
    (throw (new Exception "Insert at top"))))

(defn insert-right
  "Inserts the item as the right sibling of the node at this loc, without moving"
  [^ZipperLocation loc item]
  (if-let [path ^ZipperPath (.path loc)]
    (ZipperLocation. (.zip loc) (.node loc) (assoc path :r (cons item (.r path)) :changed? true))
    (throw (new Exception "Insert at top"))))

(defn replace
  "Replaces the node at this loc, without moving"
  [^ZipperLocation loc node]
  (ZipperLocation. (.zip loc) node (assoc (.path loc) :changed? true)))

(defn insert-child
  "Inserts the item as the leftmost child of the node at this loc, without moving"
  [^ZipperLocation loc item]
  (replace loc (make-node loc (.node loc) (cons item (children loc)))))

(defn append-child
  "Inserts the item as the rightmost child of the node at this loc, without moving"
  [^ZipperLocation loc item]
  (replace loc (make-node loc (.node loc) (concat (children loc) [item]))))

(defn next
  "Moves to the next loc in the hierarchy, depth-first. When reaching
  the end, returns a distinguished loc detectable via end?. If already
  at the end, stays there."
  [^ZipperLocation loc]
  (let [path (.path loc)]
    (if (= :end path)
      loc
      (or
        (and (branch? loc) (down loc))
        (right loc)
         (loop [p loc]
           (if (up p)
            (or (right (up p)) (recur (up p)))
            (ZipperLocation. (.zip loc) (.node p) :end)))))))

(defn prev
  "Moves to the previous loc in the hierarchy, depth-first. If already at the root, returns nil."
  [loc]
  (if-let [lloc (left loc)]
    (loop [loc lloc]
      (if-let [child (and (branch? loc) (down loc))]
        (recur (rightmost child))
        loc))
    (up loc)))

(defn end?
  "Returns true if loc represents the end of a depth-first walk"
  [^ZipperLocation loc]
  (= :end (.path loc)))

(defn remove
  "Removes the node at loc, returning the loc that would have preceded it in a depth-first walk."
  [^ZipperLocation loc]
  (if-let [path ^ZipperPath (.path loc)]
    (if (pos? (count (.l path)))
      (loop [loc (ZipperLocation. (.zip loc) (peek (.l path)) (assoc path :l (pop (.l path)) :changed? true))]
        (if-let [child (and (branch? loc) (down loc))]
          (recur (rightmost child))
          loc))
      (ZipperLocation. (.zip loc)
        (make-node loc (peek (.pnodes path)) (.r path))
        (if-let [ppath (.ppath path)] (and ppath (assoc ppath :changed? true)))))
    (throw (new Exception "Remove at top"))))

(defn edit
  "Replaces the node at this loc with the value of (f node args)"
  [^ZipperLocation loc f & args]
  (replace loc (apply f (.node loc) args)))


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