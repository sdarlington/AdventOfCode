(ns aoc2023.day09
  (:require [clojure.set]
            [clojure.string :as string])
  (:gen-class))

(defn parse-line
  [input]
  (->> (clojure.string/split input #" ")
       (map (Integer/parseInt))
       )
  )

(defn parse-input
    "Parse input"
    [input]
    (->> (string/split-lines input)
          (map parse-line)
    )
)


(defn part1
    "Advent of Code, Day 9, Part 1"
    [input]
    (->> (parse-input input)
    )
)


;; (defn part2
;;     "Advent of Code, Day 9, Part 2"
;;     [input]
;;     (->> (parse-input input)
;;          )
;; )
