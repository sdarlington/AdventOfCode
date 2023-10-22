(ns clojure-advent-of-code.day02
  (:gen-class)
  (:require clojure.string))
  
(defn load-strategy
    "Take text input and convery to something usable"
    [input]
    (map (fn [x] (clojure.string/split x #" ")) (clojure.string/split-lines input))
;    (apply hash-map (apply concat (map (fn [x] (clojure.string/split x #" ")) (clojure.string/split-lines input))))
)

(def score-map {
    "A" 1
    "B" 2
    "C" 3
    "X" 1
    "Y" 2
    "Z" 3
})

(defn score-turn
    "Scores a turn"
    [[them you]]
    (let [you-score (get score-map you) them-score (get score-map them)]
        (+ you-score (cond
            (= you-score them-score) 3
            (and (= you-score 1) (= them-score 3)) 6
            (and (= you-score 2) (= them-score 1)) 6
            (and (= you-score 3) (= them-score 2)) 6
            :else 0
        ))
    )
)

(defn part1
    "Day 2, Part 1"
    [strategy]
   (apply + (map score-turn strategy))
)

(defn score-turn-part-2
    "Scores a turn"
    [[them you]]
    (let [you-score (get score-map you) them-score (get score-map them)]
        (cond
            ; win
            (= you-score 3) (+ 6 (get { 1 2 2 3 3 1 } them-score))
            ; draw
            (= you-score 2) (+ 3 them-score)
            ; lose
            (= you-score 1) (get { 1 3 2 1 3 2 } them-score)
        )
    )
)

(defn part2
    "Day 2, Part 2"
    [strategy]
    (apply + (map score-turn-part-2 strategy))
)
