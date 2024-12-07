package main

import (
  "fmt"
  "aoc2024/day01"
  "aoc2024/day02"
  "aoc2024/day03"
  "aoc2024/day04"
  "aoc2024/day05"
  "aoc2024/day06"
  "aoc2024/day07"
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
  fmt.Println("Part 2: ", day04.Part2("day04/input.txt"))

  fmt.Println("***")

  fmt.Println("Day 5")
  fmt.Println("Part 1: ", day05.Part1("day05/input.txt"))
  // TODO: optimise
//   fmt.Println("Part 2: ", day05.Part2("day05/input.txt"))

  fmt.Println("***")

  fmt.Println("Day 6")
  fmt.Println("Part 1: ", day06.Part1("day06/input.txt"))
//   fmt.Println("Part 2: ", day06.Part2("day06/input.txt"))

  fmt.Println("***")

  fmt.Println("Day 7")
  fmt.Println("Part 1: ", day07.Part1("day07/input.txt"))
//   fmt.Println("Part 2: ", day06.Part2("day06/input.txt"))

}