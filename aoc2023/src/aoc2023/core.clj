(ns aoc2023.core
  (:gen-class)
  (:require [aoc2023.day01] [aoc2023.day02] [aoc2023.day04] [aoc2023.day09]))

(defn day01
	[]
	(let [calibrations (slurp (clojure.java.io/resource "day01-input.txt"))]
	    (println "Day 1")
	    (println (aoc2023.day01/part1 (aoc2023.day01/load-calibration calibrations)))
	    (println (aoc2023.day01/part2 calibrations))
	)
)

(defn day02
	[]
	(let [games (slurp (clojure.java.io/resource "day02-input.txt"))]
	    (println "Day 2")
	    (println (aoc2023.day02/part1 games))
	    (println (aoc2023.day02/part2 games))
	)
)

(defn day04
	[]
	(let [input (slurp (clojure.java.io/resource "day04-input.txt"))]
	    (println "Day 4")
	    (println (aoc2023.day04/part1 input))
	    (println (aoc2023.day04/part2 input))
	)
)

(defn day09
  []
  (let [input (slurp (clojure.java.io/resource "day09-input.txt"))]
    (println "Day 4")
    (println (aoc2023.day09/part1 input))
    ;; (println (aoc2023.day09/part2 input)))
  ))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (day01)
  (day02)
  (day04)
  (day09)
)
