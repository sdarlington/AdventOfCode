
// package day01

import java.io.File

fun entriesThatSum(list: Set<Int>, sumTo: Int = 2020): Set<Int> {
  for (entry in list) {
      val lookingFor = sumTo - entry
      val otherEntries = list.filter { x -> x == lookingFor }
      if (otherEntries.count() == 1) {
          return setOf(entry,otherEntries.first())
      }
  }
  return setOf()
}

fun threeEntriesThatSum(list: Set<Int>, sumTo: Int = 2020): Set<Int> {
    for (entry in list) {
        val lookingFor = sumTo - entry
        val entries = entriesThatSum(list, lookingFor)
        if (entries.count() == 2) {
            val returnEntries = entries.toMutableSet()
            returnEntries.add(entry)
            return returnEntries
        }
    }
    return setOf()
}

fun setMul(list : Set<Int>) : Int {
    return list.reduce { acc, v -> acc * v }
}

fun main() {
    // Part 1 : example
    val example = entriesThatSum(setOf(1721,979,366,299,675,1456))
    println (setMul(example))

    // Part 1 : real
    val entries = File("input.txt").readLines().map { x -> x.toInt() }.toSet()
    val part1 = entriesThatSum(entries)
    println (setMul(part1))

    // Part 2
    val part2 = threeEntriesThatSum(entries)
    println (setMul(part2))
}