(ns aoc2023.day03
  (:gen-class))

(defn parse-input
    "Parse game"
    [input]
    (->> (clojure.string/split-lines input)
         (map (fn [x] (clojure.string/split x #"")))
    )
)

(defn get-location
    "Data for point"
    [coords input]
    (nth (nth input (last coords)) (first coords))
)


(defn get-adjacent
    ""
    [line start end]

    (let [ x-range (range (- start 1) (+ end 1)) y-range (range (- line 1) (+ line 1))]
    )
)

(defn find-symbol
    ""
    [input]
    (let [line-num (first input) numbers (last input) text (first numbers) matches (last numbers)]
       (for [m matches]
         (let [index-start (clojure.string/index-of text m) index-end (+ index-start (count m) ) ]
        ;;    (index-start index-end)
        false
         )
        )
    )
)

(defn part1
    "Advent of Code, Day 2, Part 1"
    [input]
    (def parse-input (clojure.string/split-lines input))
    (->> parse-input
         (map (fn [x] (list x (re-seq #"\d+" x)))) ; find numbers
         (map list (range (count (first parse-input))))
         (map find-symbol)
    )
)


;; (defn part2
;;     "Advent of Code, Day 2, Part 2"
;;     [input]
;;     (->> (parse-input input)
;;          (map last)
;;          (map power-cubes)
;;          (reduce +)
;;     )
;; )
