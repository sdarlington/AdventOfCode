(ns aoc2023.core
  (:gen-class)
  (:require [aoc2023.day01]))

(defn day01
	[]
	(let [calibrations (slurp (clojure.java.io/resource "day01-input.txt"))]
	    (println "Day 1")
	    (println (aoc2023.day01/part1 (aoc2023.day01/load-calibration calibrations)))
	    (println (aoc2023.day01/part2 calibrations))
	)
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (day01))
