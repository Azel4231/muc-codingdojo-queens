(ns muc-codingdojo-queens.efficient
  "Somewhat more efficient version of the same solver."
  (:require [clojure.pprint :as p]))

(def dimension 8)
(defn all-positions [dimension] (for [x (range dimension)]
                                  (for [y (range dimension)]
                                    [x y])))

(def y-coord second)
(def diagonal1 (partial apply +))
(def diagonal2 (partial apply -))

(defn same [f coll]
  (apply = (map f coll)))

(defn beats? [& positions]
  (or (same y-coord positions)
      (same diagonal1 positions)
      (same diagonal2 positions)))

(defn solutions [positions chosen]
  (if (empty? positions)
    [chosen]
    (mapcat (fn [chosen-pos]
              (solutions (map #(remove (partial beats? chosen-pos) %)
                              (rest positions))
                         (conj chosen chosen-pos)))
            (first positions))))

(let [result (time (solutions (all-positions dimension) []))]
  (println (count result) " results (" dimension "x" dimension ")")  )
;; "Elapsed time: 30.79253 msecs"
;; 92  results ( 8 x 8 )


;; instead of:
;; 92  results ( 8 x 8 ):
;; "Elapsed time: 38448.036158 msecs"
;; in clean.clj