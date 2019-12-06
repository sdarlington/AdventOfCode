
val inputFile = io.Source.fromFile("input.txt")

val orbits = inputFile.getLines.map (x => {
                                      val y = x.split(')')
                                      (y(1), y(0))
                                    }).toMap

inputFile.close()

def countOrbits(o : String) : Int = {
    val isOrbitedBy = orbits(o)
    println(s"$o <- $isOrbitedBy")
    if (orbits.contains(isOrbitedBy)) { countOrbits(isOrbitedBy) + 1 } else { 1 }
}

println(orbits.map({ case (o1,o2) => countOrbits(o1) }).reduce(_+_))