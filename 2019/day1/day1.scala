
// Examples in description
val checkSamples = checkAnswers(Seq(12,14,1969,100756)) _

def checkAnswers(input : Seq[Long])(f : Long => Long, answers : Seq[Long]) = {
  input
    .zip(answers)
    .map(m => (m._1, f(m._1), m._2))
    .filter(x => x._2 != x._3)
    .map({ case (m,f,a) => s"Fuel required for mass $m is $a but got $f." })
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
def fuelRequired(mass : Long) : Long = mass / 3 - 2

// Check we're heading in the right direction
checkSamples(fuelRequired, Seq(2,2,654,33583))

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
checkSamples(fuelRequredForFuel, Seq(2, 2, 966, 50346))

// All requiements (part 2)
val totalFuel2 = runTest(fuelRequredForFuel)

println (s"Total fuel required in part 2: $totalFuel2")
