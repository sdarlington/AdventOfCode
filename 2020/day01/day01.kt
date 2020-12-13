
// package day01

import java.io.File

fun entriesThatSum(list: Set<Int>, sumTo: Int = 2020): Pair<Int,Int>  {
  for (entry in list) {
      val lookingFor = sumTo - entry
      val otherEntries = list.filter { x -> x == lookingFor }
      if (otherEntries.count() == 1) {
          return Pair(entry,otherEntries.first())
      }
  }
  return Pair(0,0)
}

fun threeEntriesThatSum(list: Set<Int>, sumTo: Int = 2020): Set<Int> {
    for (entry in list) {
        val lookingFor = sumTo - entry
        val (v1,v2) = entriesThatSum(list, lookingFor)
        if (v1 != 0 && v2 != 0) {
            return setOf(entry,v1,v2)
        }
    }
    return setOf(0,0,0)
}

fun main() {
    // Part 1 : example
    val (v1,v2) = entriesThatSum(setOf(1721,979,366,299,675,1456))
    println (v1*v2)

    // Part 1 : real
    val entries = File("input.txt").readLines().map { x -> x.toInt() }.toSet()
    val (v3,v4) = entriesThatSum(entries)
    println (v3*v4)

    // Part 2
    val part2 = threeEntriesThatSum(entries)
    println (part2.reduce { acc, v -> acc * v })
}