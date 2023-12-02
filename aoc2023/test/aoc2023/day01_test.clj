(ns aoc2023.day01-test
  (:require [clojure.test :refer :all]
            [clojure.java.io]
            [aoc2023.day01 :as day01]))

(def sample-input (slurp (clojure.java.io/resource "day01-sample.txt")))
(def sample-input2 (slurp (clojure.java.io/resource "day01-sample2.txt")))
(def sample-calibration (day01/load-calibration sample-input))

(deftest test-part1
  (testing "Day 1 Part 1"
    (is (= 142 (day01/part1 sample-calibration)))
  )
)

(deftest test-part2
  (testing "Day 1 Part 2"
    (is (= 281 (day01/part2 sample-input2)))
  )
)