(ns muc-codingdojo-queens.core
  "Code I showed at the end of the coding dojo. See clean.clj for a cleaner version."
  (:require [clojure.pprint :as p]
            [clojure.math.combinatorics :as c]))

(def dimension 8)

(def empty-line (into [] (repeat dimension :_)))
(def empty-board (into [] (repeat dimension empty-line)))
(p/pprint empty-board)

(def all-positions (for [x (range dimension)]
                     (for [y (range dimension)]
                       [x y])))

(def all-combinations (apply c/cartesian-product all-positions))

(defn none-in-same-row? [combination]
  (= dimension (count (distinct (map second combination)))))

(defn none-in-same-column? [combination]
  (= dimension (count (distinct (map first combination)))))

(defn none-on-same-diagonal? [combination]
  (= dimension (count (distinct (map (partial apply +) combination)))))

(defn none-on-same-other-diagonal? [combination]
  (= dimension (count (distinct (map (partial apply -) combination)))))

(defn valid? [combination]
  ((every-pred none-in-same-row? 
               none-in-same-column?
               none-on-same-diagonal?
               none-on-same-other-diagonal?)
    combination))

(def result (filter valid? all-combinations))

(println (count result))
