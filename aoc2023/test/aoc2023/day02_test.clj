(ns aoc2023.day02-test
  (:require [clojure.test :refer :all]
            [clojure.java.io]
            [aoc2023.day02 :as day01]))

(def sample-input (slurp (clojure.java.io/resource "day02-sample.txt")))

(deftest test-part1
  (testing "Day 2 Part 1"
    (is (= 8 (day01/part1 sample-input)))
  )
)

(deftest test-part2
  (testing "Day 2 Part 2"
    (is (= 2286 (day01/part2 sample-input)))
  )
)