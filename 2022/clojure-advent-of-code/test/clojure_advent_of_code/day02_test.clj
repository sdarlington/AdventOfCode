(ns clojure-advent-of-code.day02-test
  (:require [clojure.test :refer :all]
            [clojure.java.io]
            [clojure-advent-of-code.day02 :as day02]))


(def sample-input (clojure.java.io/resource "day02-sample.txt"))
(def sample-strategy (day02/load-strategy (slurp sample-input)) )

(deftest test-part1
  (testing "Day 2 Part 1"
    (is (= 15 (day02/part1 sample-strategy)))
  )
)

(deftest test-part2
 (testing "Day 2 Part 2"
   (is (= 12 (day02/part2 sample-strategy)))
 )
)
