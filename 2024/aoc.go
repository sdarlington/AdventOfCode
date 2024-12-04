package main

import (
  "fmt"
  "aoc2024/day01"
  "aoc2024/day02"
  "aoc2024/day03"
  "aoc2024/day04"
)

func main() {
  fmt.Println("Day 1")
  fmt.Println("Part 1: ", day01.Part1("day01/input.txt"))
  fmt.Println("Part 2: ", day01.Part2("day01/input.txt"))

  fmt.Println("***")

  fmt.Println("Day 2")
  fmt.Println("Part 1: ", day02.Part1("day02/input.txt"))
  fmt.Println("Part 2: ", day02.Part2("day02/input.txt"))

  fmt.Println("***")

  fmt.Println("Day 3")
  fmt.Println("Part 1: ", day03.Part1("day03/input.txt"))
  fmt.Println("Part 2: ", day03.Part2("day03/input.txt"))

  fmt.Println("***")

  fmt.Println("Day 4")
  fmt.Println("Part 1: ", day04.Part1("day04/input.txt"))
//   fmt.Println("Part 2: ", day04.Part2("day04/input.txt"))

}