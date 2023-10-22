(ns clojure-advent-of-code.day01
  (:gen-class)
  (:require clojure.string))

(defn load-elves
    [x]
    (load-string (str "[[" (clojure.string/join " " (replace { "" "] [" } (clojure.string/split-lines (slurp x)))) "]]")))


(defn parse-input
    "Parse input"
    [calorie-list]
    (sort (map (fn [x] (apply + x)) calorie-list)))

(defn part1
    "Advent of Code, Day 1, Part 1"
    [elves]
    (last (parse-input elves))
)

(defn part2
    "Advent of Code, Day 1, Part 2"
    [elves]
    (apply + (take 3 (reverse (parse-input elves))))
)
