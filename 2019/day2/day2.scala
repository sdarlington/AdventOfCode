
def convertToList(input : String) : Array[Int] = input.split(",").map(_.toInt)

// ---------
// Part 1
def runCalculation(input : Array[Int]) : Array[Int] = {
  var memory = new VirtualMachine(input.toBuffer)
  while (memory.step) {}
  memory.allMemory
}

import scala.collection.mutable.Buffer

class VirtualMachine(memory : Buffer[Int]) {
  var pc = 0

  def incPC(inc : Int = 1) : Unit = pc += inc

  def offsetValue(offset : Int) : Int = memory(pc + offset)

  def value(address : Int) : Int = memory(address)

  def setValue(address : Int, value : Int) : Unit = memory(address) = value

  def allMemory() : Array[Int] = memory.toArray[Int]

  def print() : Unit = println(memory.mkString(","))

  def step() : Boolean = opcode(offsetValue(0))()

  val opcode = Instructions.values.toList.map(x => (x.opcode, x.code)).toMap

  object Instructions extends Enumeration {
    protected case class Val(opcode : Int, code : () => Boolean) extends super.Val 

    import scala.language.implicitConversions
    implicit def valueToInstructionVal(x: Value): Val = x.asInstanceOf[Val]

    val Add = Val(1, () => {
      setValue(offsetValue(3), value(offsetValue(1)) + value(offsetValue(2)))
      incPC(4)
      true
    })
    val Multiply = Val(2, () => {
      setValue(offsetValue(3), value(offsetValue(1)) * value(offsetValue(2)))
      incPC(4)
      true
    })
    val Halt = Val(99, () => false)
  }

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
