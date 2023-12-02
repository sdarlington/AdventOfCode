(ns aoc2023.day02
  (:gen-class))

(defn parse-game
    "Parse game"
    [game]
    (->> (clojure.string/split game #", ")
         (map (fn [x] (clojure.string/split x #" ")))
         (map reverse)
         (flatten)
         (apply hash-map)
    )
)

(defn parse-row
    "Parse row"
    [input]
    (let [x (re-matches #"Game (\d+): (.*)" input)]
           (list (load-string (x 1))
                (->> (clojure.string/split (x 2) #"; ")
                      (map parse-game)
                 )
           )
         )
)

(defn parse-input
    "Parse input"
    [input]
    (->> (clojure.string/split-lines input)
         (map parse-row)
    )
)

(defn test-colour
    "Test colour"
    [game colour limit]
    (let [x (game colour)]
        (or (nil? x) (<= (load-string x) limit)
        )
    )
)

(defn part1-filter
    "12 red, 13 green, 14 blue"
    [game]
    (->> game
         (map (fn [x]
               (and (test-colour x "red" 12) (test-colour x "green" 13) (test-colour x "blue" 14))
             )
         )
         (some false?)
         (not)
    )
)

(defn part1
    "Advent of Code, Day 2, Part 1"
    [input]
    (->> (parse-input input)
         (filter (fn [x] (part1-filter (last x))))
         (map first)
         (apply +)
    )
)

(defn part2
    "Advent of Code, Day 2, Part 2"
    [input]
    input
)
