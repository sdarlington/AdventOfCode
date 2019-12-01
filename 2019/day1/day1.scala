
// Examples in decsription
def checkSamples(f : Long => Long) = {
  Seq(12,14,1969,100756)
       .map(m => (m, f(m)))
       .map({ case (m,f) => s"Fuel required for mass $m is $f." })
       .foreach(println(_))
}

def runTest(f : Long => Long) : Long = {
    val inputFile = io.Source.fromFile("input.txt")
    try inputFile
            .getLines
            .map(_.toLong)
            .map(f(_))
            .reduce(_+_)
    finally inputFile.close()
}

// Part 1
def fuelRequired(mass : Long) : Long = {
  mass / 3 - 2
}

// Check we're heading in the right direction
checkSamples(fuelRequired)

// All requirements (part 1)
val totalFuel = runTest(fuelRequired)
println (s"Total fuel required in part 1: $totalFuel")

// Part 2
def fuelRequredForFuel(fuel : Long) : Long = {
    val f = fuelRequired(fuel)
    if (f < 0) {
        0
    }
    else {
        f + fuelRequredForFuel(f)
    }
}

// Examples in description
checkSamples(fuelRequredForFuel)

// All requiements (part 2)
val totalFuel2 = runTest(fuelRequredForFuel)

println (s"Total fuel required in part 2: $totalFuel2")
