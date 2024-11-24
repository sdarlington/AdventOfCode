(ns aoc2023.day04
  (:require [clojure.set]
            [clojure.string :as string])
  (:gen-class))

(defn parse-card 
  [text]
  (->> (clojure.string/split text #" +")
       (last)
       (Integer/parseInt)
       )
  )

(defn parse-line
  [line]
  (let [d (string/split line #": ")]
    (->> (string/split (last d) #" +\| +")
         (map (fn [x] (string/split x #" +")))
         (list (parse-card (first d)))
         )
    )
  )

(defn parse-input
    "Parse game"
    [input]
    (->> (string/split-lines input)
          (map parse-line)
    )
)

(defn calculate-line
  [line]
  (let [hand (first line) cards (last line) winning (first cards) my-cards (last cards)]
    (list hand
    (->> (clojure.set/intersection (set winning) (set my-cards))
         (count)
         )
    ))
  )

(defn score-line
    [matches]
    (if (> matches 0)
      (int (Math/pow 2 (- matches 1)))
      0
    )
)

(defn part1
    "Advent of Code, Day 4, Part 1"
    [input]
    (->> (parse-input input)
         (map calculate-line)
         (map last)
         (map score-line)
         (apply +)
    )
)


(defn part2
    "Advent of Code, Day 4, Part 2"
    [input]
    (->> (parse-input input)
         (map calculate-line)
         )
)
