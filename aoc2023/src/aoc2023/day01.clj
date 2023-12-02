(ns aoc2023.day01
  (:gen-class))

(defn load-calibration
    [input]
    (map (fn [x] (clojure.string/split x #"")) (clojure.string/split-lines input)))

(defn number-filter [x] (not (nil? (some #{x} (map str (range 10))))))

(defn only-numbers [input] (map (fn [x] (filter number-filter x)) input))

(defn first-and-last [input] (map (fn [x] (load-string (clojure.string/join (list (first x) (last x))))) input))

(defn part1
    "Advent of Code, Day 1, Part 1"
    [calibration]
    (apply + (first-and-last (only-numbers calibration)))
)

(def number-mapping {
    "one" "1"
    "two" "2"
    "three" "3"
    "four" "4"
    "five" "5"
    "six" "6"
    "seven" "7"
    "eight" "8"
    "nine" "9"

    "oneight" "18"
    "twone" "21"
    "threeight" "38"
    "fiveight" "58"
    "sevenine" "79"
    "eightwo" "82"
    "eighthree" "83"
    "nineight" "98"
})

(defn preprocess
    [input]
    (clojure.string/replace input #"(twone|threeight|nineight|fiveight|sevenine|eightwo|eighthree|oneight|one|two|three|four|five|six|seven|eight|nine)"
       (fn [[a]] (number-mapping a))
    )
)

(defn part2
    "Advent of Code, Day 1, Part 2"
    [calibration]
    (->> (clojure.string/split-lines calibration)
         (map preprocess)
         (map (fn [x] (clojure.string/split x #"")))
         (only-numbers)
         (first-and-last)
         (apply +)
    )
)
