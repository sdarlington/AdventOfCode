(ns aoc2023.core
  (:gen-class)
  (:require [aoc2023.day01] [aoc2023.day02]))

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

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (day01)
  (day02)
)
