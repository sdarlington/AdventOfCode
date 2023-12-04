(ns aoc2023.day04
  (:require [clojure.set])
  (:gen-class))

(defn parse-line
    ""
    [line]
    (let [d (clojure.string/split line #": ")]
        (->> (clojure.string/split (last d) #" +\| +")
             (map (fn [x] (clojure.string/split x #" +")))
             (list (first d))
        )
    )
)

(defn parse-input
    "Parse game"
    [input]
    (->> (clojure.string/split-lines input)
          (map parse-line)
    )
)

(defn calculate-line
    ""
    [line]
    (let [cards (last line) winning (first cards) my-cards (last cards)]
        (->> (clojure.set/intersection (set winning) (set my-cards))
             (count)
        )
    )
)

(defn score-line
    [matches]
    (if (> matches 0)
      (int (Math/pow 2 (- matches 1)))
      0
    )
)

; not 26272 (too high)
(defn part1
    "Advent of Code, Day 2, Part 1"
    [input]
    (->> (parse-input input)
         (map calculate-line)
         (map score-line)
         (apply +)
    )
)


;; (defn part2
;;     "Advent of Code, Day 2, Part 2"
;;     [input]
;;     (->> (parse-input input)
;;          (map last)
;;          (map power-cubes)
;;          (reduce +)
;;     )
;; )
