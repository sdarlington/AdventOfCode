(ns clojure-advent-of-code.day01-test
  (:require [clojure.test :refer :all]
            [clojure.java.io]
            [clojure-advent-of-code.day01 :as day01]))


(def sample-input (clojure.java.io/resource "day01-input.txt"))
(def sample-elves (day01/load-elves sample-input))

(deftest test-part1
  (testing "Day 1 Part 1"
    (is (= 24000 (day01/part1 sample-elves)))
  )
)

(deftest test-part2
  (testing "Day 1 Part 2"
    (is (= 45000 (day01/part2 sample-elves)))
  )
)