(ns muc-codingdojo-queens.core
  "Code I showed at the end of the coding dojo. See clean.clj for a cleaner version."
  (:require [clojure.pprint :as p]
            [clojure.math.combinatorics :as c]))

(def dimension 8)

(def empty-line (into [] (repeat dimension :_)))
(def empty-board (into [] (repeat dimension empty-line)))
(p/pprint empty-board)

;; Enumerate all the positions on the board as [x y] tuples
(def all-positions (for [x (range dimension)]
                     (for [y (range dimension)]
                       [x y])))

;; Use a library to calculate all possible combinations of queen placements, if queens are placed each in their own row.
;; This means 8 to the power of 8 = 16.7M combinations
(def all-combinations (apply c/cartesian-product all-positions))

;; check for queens in same column
(defn none-in-same-column? [combination]
  (= dimension (count (distinct (map second combination)))))

;; check for queens in same row (not necessary because those combinations are not included)
(defn none-in-same-row? [combination]
  (= dimension (count (distinct (map first combination)))))

;; check for queens in same diagonal
(defn none-on-same-diagonal? [combination]
  (= dimension (count (distinct (map (partial apply +) combination)))))

;; check for queens in same other diagonal
(defn none-on-same-other-diagonal? [combination]
  (= dimension (count (distinct (map (partial apply -) combination)))))

;; combine all rules/predicates to one predicate
(defn valid? [combination]
  ((every-pred none-in-same-row? 
               none-in-same-column?
               none-on-same-diagonal?
               none-on-same-other-diagonal?)
    combination))

;; filter all 16.7M combinations to find the 92 valid ones
(def result (filter valid? all-combinations))

(println (count result))
