
import java.io.File

enum class Geology { TREE, OPEN }
fun Geology(x : Char) : Geology = when (x) {
    '#' -> Geology.TREE
    else -> Geology.OPEN
}

class Map (val map: List<List<Geology>>) {
    fun run1(dx : Int = 3, dy : Int = 1) : Int {
        var x = 0
        var y = 0
        var treeCount = 0

        while (y < map.count()) {
            val isTree = geologyAt(x,y) == Geology.TREE
            if (isTree) {
                treeCount++
            }

            x += dx
            y += dy
        }

        return treeCount
    }

    fun geologyAt(x : Int, y : Int) : Geology {
        val row = map.getOrElse(y) { listOf() }
        val space = row.getOrElse(x % row.count()) { Geology.OPEN }
        return space
    }

    fun print() {
        for (l in map) {
            for (p in l) {
                when (p) {
                    Geology.TREE -> print("#")
                    Geology.OPEN -> print(" ")
                    }
            }
            println ()
        }
    }
}

fun Map(file : String) : Map {
    val entries = File(file).readLines().map { x -> parseLine(x) }
    return Map(entries)
}

fun parseLine(line : String) : List<Geology> = line.map { x -> Geology(x) }

fun main() {
    // Example
    val map = Map("example.txt")
     println (map.run1())

     // Part 1
    val map2 = Map("input.txt")
    println (map2.run1())

    // Part 2
    val slopes = listOf(
         Pair(1,1),
         Pair(3,1),
         Pair(5,1),
         Pair(7,1),
         Pair(1,2)
         )
    val results = slopes.map { x -> map2.run1(x.first, x.second) }
                        .map { x -> x.toLong() }
                        .reduce { acc, v -> acc * v }

    println(results)
}