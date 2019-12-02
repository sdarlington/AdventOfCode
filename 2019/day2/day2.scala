
def convertToList(input : String) : Array[Int] = input.split(",").map(_.toInt)

// ---------
// Part 1
def runCalculation(input : Array[Int]) : Array[Int] = {
  import scala.collection.mutable.ArrayBuffer

  var memory = input.toBuffer

  var pc = 0
  while (memory(pc) != 99) {
    val arg1 = memory(memory(pc + 1))
    val arg2 = memory(memory(pc + 2))
    val fn = memory(pc) match {
      case 1 => (arg1 : Int, arg2 : Int) => arg1 + arg2
      case 2 => (arg1 : Int, arg2 : Int) => arg1 * arg2
      case _ => (arg1 : Int, arg2 : Int) => -1
    }
  
    memory(memory(pc + 3)) = fn(arg1, arg2)
    pc += 4
  }
  memory.toArray[Int]
}

// Test data
val testData = Seq(
  ("1,0,0,0,99","2,0,0,0,99"),
  ("2,3,0,3,99","2,3,0,6,99"),
  ("2,4,4,5,99,0","2,4,4,5,99,9801"),
  ("1,1,1,4,99,5,6,0,99","30,1,1,4,2,5,6,0,99")
)

// Examples
testData.foreach({ case (i,o) =>
  val input = convertToList(i)
  val expected = convertToList(o)
  val result = runCalculation(input)
  if(!expected.zip(result).map({ case (e,r) => e == r }).reduce(_ && _)) {
    println ("Input   : " + input.mkString(","))
    println ("Expected: " + expected.mkString(","))
    println ("Result  : " + result.mkString(","))
  }
})

// Go for it...
val inputFile = io.Source.fromFile("input.txt")
val input1 = try inputFile
                   .getLines
                   .next
             finally inputFile.close()

var program = convertToList(input1).toBuffer
program(1) = 12
program(2) = 2
println("Part 1")
println(runCalculation(program.toArray)(0))

// Part 2
println()
println("Part 2")
for (noun <- 0 to 99; verb <- 0 to 99) {
  program(1) = noun
  program(2) = verb
  if (runCalculation(program.toArray)(0) == 19690720) {
    println ("Noun: " + noun)
    println ("Verb: " + verb)
  }
}
