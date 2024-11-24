(ns aoc2023.day05-test
  (:require [clojure.test :refer :all]
            [clojure.java.io]
            [aoc2023.day05 :as day05]))

(def sample-input (slurp (clojure.java.io/resource "day05-sample.txt")))

(deftest test-part1
  (testing "Day 5 Part 1"
    (is (= 35 (day05/part1 sample-input)))
  )
)

;; (deftest test-part2
;;   (testing "Day 5 Part 2"
;;     (is (= 30 (day05/part2 sample-input)))
;;   )
;; )