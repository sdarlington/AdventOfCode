
val inputFile = io.Source.fromFile("input.txt")

val orbits = inputFile.getLines.map (x => {
                                      val y = x.split(')')
                                      (y(1), y(0))
                                    }).toMap

inputFile.close()

def countOrbits(o : String) : Int = {
    val isOrbitedBy = orbits(o)
    // println(s"$o <- $isOrbitedBy")
    if (orbits.contains(isOrbitedBy)) { countOrbits(isOrbitedBy) + 1 } else { 1 }
}

println("Part1:")
println(orbits.map({ case (o1,o2) => countOrbits(o1) }).reduce(_+_))
println()


val inputFile2 = io.Source.fromFile("input.txt")

val orbits2 = inputFile2.getLines.map (x => {
                                      val y = x.split(')')
                                      (y(1), y(0))
                                    }).toMap

inputFile2.close()

import scala.collection.mutable.ListBuffer
def listOrbits(from : String) : ListBuffer[String] = {
    var current = from
    val list = ListBuffer[String]()
    while (orbits2.contains(current)) {
        list += current
        current = orbits2(current)
    }
    list
}

val me = listOrbits("YOU")
val santa = listOrbits("SAN")
val connection = me.toSet.intersect(santa.toSet)

val meHopsToCommon = connection.map(me.indexOf(_)).min - 1
val saHopsToCommon = connection.map(santa.indexOf(_)).min - 1

println ("Part 2:\n" + (meHopsToCommon + saHopsToCommon))
