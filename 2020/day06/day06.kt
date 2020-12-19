

import java.io.File

class Declarations() {
    fun readFile(file : String) : List<List<List<Char>>> {
        val entries = File(file).readLines()

        val groups : MutableList<List<List<Char>>> = mutableListOf()
        var people : MutableList<List<Char>> = mutableListOf()
        for (line in entries) {
            if (line.count() == 0) {
                groups.add(people)
                people = mutableListOf()
            }
            else {
                people.add(line.toList())
            }
        }
        if (people.count() > 0) {
            groups.add(people)
        }

        return groups
    }

    fun uniqueAnswers(answers : List<List<Char>>) : Int {
        return answers.map { x -> x.toSet() }
                      .reduce { acc, v -> acc.union(v) }
                      .count()
    }

    fun commonAnswers(answers : List<List<Char>>) : Int {
        return answers.map { x -> x.toSet() }
                      .reduce { acc, v -> acc.intersect(v) }
                      .count()
    }
}

fun main() {
    // sample
    val decl = Declarations()
    val sample = decl.readFile("example.txt")
    val sampleCount = sample.map { x -> decl.uniqueAnswers(x) }
                            .reduce { acc, v -> acc + v }
    println (sampleCount)
                          
    println ("Part 1")
    val input = decl.readFile("input.txt")
    val step1Count = input.map { x -> decl.uniqueAnswers(x) }
                          .reduce { acc, v -> acc + v }
    println (step1Count)

    println ("Part 2")
    val sampleCount2 = sample.map { x -> decl.commonAnswers(x) }
                             .reduce { acc, v -> acc + v }
    println (sampleCount2)

    val step2Count = input.map { x -> decl.commonAnswers(x) }
                          .reduce { acc, v -> acc + v }
    println (step2Count)

}