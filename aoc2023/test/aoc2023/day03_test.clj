(ns aoc2023.day03-test
  (:require [clojure.test :refer :all]
            [clojure.java.io]
            [aoc2023.day03 :as day03]))

(def sample-input (slurp (clojure.java.io/resource "day03-sample.txt")))

(deftest test-part1
  (testing "Day 3 Part 1"
    (is (= "4361" (day03/part1 sample-input)))
  )
)

;; (deftest test-part2
;;   (testing "Day 3 Part 2"
;;     (is (= 2286 (day01/part2 sample-input)))
;;   )
;; )