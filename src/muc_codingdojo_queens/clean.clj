(ns muc-codingdojo-queens.clean
  "Somewhat cleaner version of the same solver."
  (:require [clojure.pprint :as p]
            [clojure.math.combinatorics :as c]))

(def dimension 8)

;; Could also be done using c/selections
(def all-positions (for [x (range dimension)]
                     (for [y (range dimension)]
                       [x y])))

;; avoid a lot of code by using a library
(defn all-combinations []
  (apply c/cartesian-product all-positions))

;; higher order function that allows expressing the rules more concisely
(defn unique [f]
  (fn [coll] (apply distinct? (map f coll))))

;; function overloading to make code below more readable
(def y-coord second)
(def diagonal1 (partial apply +))
(def diagonal2 (partial apply -))

(def valid?
  (every-pred (unique y-coord)
              (unique diagonal1)
              (unique diagonal2)))

(def result (filter valid? (all-combinations)))

(time (println (count result) " results (" dimension "x" dimension "):"))

;; 92  results ( 8 x 8 ):
;; "Elapsed time: 38448.036158 msecs"

(def empty-line (into [] (repeat dimension :_)))
(def empty-board (into [] (repeat dimension empty-line)))

;; Place the queens to the board. Use the x/y tuples as "path" into the two-dimensional array (assoc-in does this).
(defn add-to-board [board positions]
  (reduce #(assoc-in %1 %2 :Q)
          board
          positions))

;; print all solutions
(p/pprint (map #(add-to-board empty-board %) result))

(comment
  "4 results ( 6 x 6 ):
 ([[:_ :Q :_ :_ :_ :_]
   [:_ :_ :_ :Q :_ :_]
   [:_ :_ :_ :_ :_ :Q]
   [:Q :_ :_ :_ :_ :_]
   [:_ :_ :Q :_ :_ :_]
   [:_ :_ :_ :_ :Q :_]]
  [[:_ :_ :Q :_ :_ :_]
   [:_ :_ :_ :_ :_ :Q]
   [:_ :Q :_ :_ :_ :_]
   [:_ :_ :_ :_ :Q :_]
   [:Q :_ :_ :_ :_ :_]
   [:_ :_ :_ :Q :_ :_]]
  [[:_ :_ :_ :Q :_ :_]
   [:Q :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :Q :_]
   [:_ :Q :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :Q]
   [:_ :_ :Q :_ :_ :_]]
  [[:_ :_ :_ :_ :Q :_]
   [:_ :_ :Q :_ :_ :_]
   [:Q :_ :_ :_ :_ :_]
   [:_ :_ :_ :_ :_ :Q]
   [:_ :_ :_ :Q :_ :_]
   [:_ :Q :_ :_ :_ :_]])")

