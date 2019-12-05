
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

  def value(address : Int, mode : ParameterMode.Value = ParameterMode.Position) : Int = {
      val retv = mode match {
          case ParameterMode.Position => memory(memory(address))
          case ParameterMode.Immediate => memory(address)
      }
    //   println(s"Getting: $address Mode: $mode Value: $retv")
      retv
  }

  def setValue(address : Int, value : Int) : Unit = memory(address) = value

  def allMemory() : Array[Int] = memory.toArray[Int]

  def printMemory() : Unit = println(memory.mkString(","))

  def printCommands() : Unit = {
      var c = 0
      while (c < 200) {
          val command = value(c, ParameterMode.Immediate)
        val (instruction,modes) = instructionToOpcodeParameterMode(command)
        print (s"$c  $instruction ($command) ")
        for (i <- 1 to parameters(instruction)) {
            print (s" ${value(c+i,ParameterMode.Immediate)} ")
        }
        println()
        c += parameters(instruction) + 1
      }
  }

  object ParameterMode extends Enumeration {
      type ParameterMode = Value
      val Position, Immediate = Value
  }

  def instructionToOpcodeParameterMode(instruction : Int) : (Int,Seq[ParameterMode.Value]) = {
    val opcode = if (instruction < 100) { instruction  } else { instruction % 100 }
    // println (s"Opcode: $opcode")
    // printMemory
    val modeCount = parameters(opcode)
    val modesList = (instruction / 100)
                   .toString
                   .reverse
    val modes = modesList
                   .concat("0" * (modeCount - modesList.length))
                   .map(_ match {
                       case '0' => ParameterMode.Position
                       case '1' => ParameterMode.Immediate
                   })
    (opcode,modes)
  }

  def step() : Boolean = {
      val (instruction,modes) = instructionToOpcodeParameterMode(value(pc, ParameterMode.Immediate))
      val retv = opcode(instruction)(modes)
      incPC(parameters(instruction) + 1)
    //   println("---")
      retv
  }

  val opcode = Instructions.values.toList.map(x => (x.opcode, x.code)).toMap
  val parameters = Instructions.values.toList.map(x => (x.opcode, x.paramCount)).toMap

  object Instructions extends Enumeration {
    protected case class Val(opcode : Int, paramCount : Int, code : (Seq[ParameterMode.Value]) => Boolean) extends super.Val 

    import scala.language.implicitConversions
    implicit def valueToInstructionVal(x: Value): Val = x.asInstanceOf[Val]

    val Add = Val(1, 3, (p) => {
        // println(s"ADD: ${value(pc + 1, p(0))} / ${value(pc + 2, p(1))} / ${value(pc + 3, ParameterMode.Immediate)}")
        // println (s"ADD:: ${p(0)} ${p(1)} ${p(2)}")
        // println(s"ADD:: ${value(pc + 1, ParameterMode.Immediate) } / ${value(pc + 2, ParameterMode.Immediate) } / ${pc + 3}")
        // println(s"ADD:: ${value(pc + 1, ParameterMode.Position) } / ${value(pc + 2, ParameterMode.Position) } / ${pc + 3}")
      setValue(value(pc + 3, ParameterMode.Immediate), value(pc + 1, p(0)) + value(pc + 2, p(1)))
    //   incPC(4)
      true
    })
    val Multiply = Val(2, 3, (p) => {
        // println(s"MUL: ${value(pc + 1, p(0))} / ${value(pc + 2, p(1))} / ${value(pc + 3, ParameterMode.Immediate)}")
        // println (s"MUL:: ${p(0)} ${p(1)} ${p(2)}")
        // println(s"MUL:: ${value(pc + 1, ParameterMode.Immediate) } / ${value(pc + 2, ParameterMode.Immediate) } / ${pc + 3}")
        // println(s"MUL:: ${value(pc + 1, ParameterMode.Position) } / ${value(pc + 2, ParameterMode.Position) } / ${pc + 3}")
      setValue(value(pc + 3, ParameterMode.Immediate), value(pc + 1, p(0)) * value(pc + 2, p(1)))
    //   incPC(4)
      true
    })
    val IntegerInput = Val(3, 1, (p) => {
        println("Enter integer: ")
        val num = scala.io.StdIn.readLine().toInt
        setValue(value(pc + 1, ParameterMode.Immediate), num)
        true
    })
    val IntegerOutput = Val(4, 1, (p) => {
        println("Diagnostic: " + value(pc + 1, p(0)) + " @ memory: " + pc)
        true
    })
    val JumpIfTrue = Val(5, 2, (p) => {
        if (value(pc + 1, p(0)) != 0) {
            // PC will be incremented by 3 on returm
            pc = value(pc + 2, p(1)) - 3
        }
        true
    })
    val JumpIfFalse = Val(6,2, (p) => {
        if (value(pc + 1, p(0)) == 0) {
            pc = value(pc + 2, p(1)) - 3
        }
        true
    })
    val LessThan = Val(7, 3, (p) => {
        setValue(value(pc + 3, ParameterMode.Immediate),
          if (value(pc + 1, p(0)) < value(pc + 2, p(1))) {
            1
          }
          else {
              0
          })
          true
    })
    val Equals = Val(8, 3, (p) => {
        setValue(value(pc + 3, ParameterMode.Immediate),
          if (value(pc + 1, p(0)) == value(pc + 2, p(1))) {
            1
          }
          else {
              0
          })
          true
    })
    val Halt = Val(99, 0, (p) => false)
  }

}

// Test data
val testData = Seq(
  ("1,0,0,0,99","2,0,0,0,99"),
  ("2,3,0,3,99","2,3,0,6,99"),
  ("2,4,4,5,99,0","2,4,4,5,99,9801"),
  ("1,1,1,4,99,5,6,0,99","30,1,1,4,2,5,6,0,99"),
  ("1002,4,3,4,33","1002,4,3,4,99"),
  ("101,1,4,4,98","101,1,4,4,99")
)

// Examples
testData.foreach({ case (i,o) =>
println(s"Running: $i")
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

var program = convertToList(input1)
println("Part 1")
println(runCalculation(program)(0))


// Part2
// Seq("3,9,8,9,10,9,4,9,99,-1,8","3,9,7,9,10,9,4,9,99,-1,8","3,3,1108,-1,8,3,4,3,99", "3,3,1107,-1,8,3,4,3,99")

Seq("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99")
  .foreach( x => {
      val z = convertToList(x)
runCalculation(z)
  })
