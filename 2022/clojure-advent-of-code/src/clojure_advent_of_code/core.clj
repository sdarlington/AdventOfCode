(ns clojure-advent-of-code.core
  (:gen-class)
  (:require [clojure-advent-of-code.day01]
            [clojure-advent-of-code.day02]))

(defn day01
	[]
	(let [elves (clojure-advent-of-code.day01/load-elves "/Users/stephend/Documents/Development/adventofcode/2022/day01/input.txt")]
	    (println "Day 1")
	    (println (clojure-advent-of-code.day01/part1 elves))
	    (println (clojure-advent-of-code.day01/part2 elves))
	)
)

(defn day02
	[]
	(let [strategy (clojure-advent-of-code.day02/load-strategy (slurp "/Users/stephend/Documents/Development/adventofcode/2022/day02/input.txt"))]
	    (println "Day 2")
	    (println (clojure-advent-of-code.day02/part1 strategy))
        (println (clojure-advent-of-code.day02/part2 strategy))
    )
)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (day01)
  (day02)
  )

