(ns aoc2023.day04-test
  (:require [clojure.test :refer :all]
            [clojure.java.io]
            [aoc2023.day04 :as day04]))

(def sample-input (slurp (clojure.java.io/resource "day04-sample.txt")))

(deftest test-part1
  (testing "Day 4 Part 1"
    (is (= 13 (day04/part1 sample-input)))
  )
)

;; (deftest test-part2
;;   (testing "Day 3 Part 2"
;;     (is (= 2286 (day01/part2 sample-input)))
;;   )
;; )