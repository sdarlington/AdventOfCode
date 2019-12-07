

import scala.collection.mutable.Buffer

class VirtualMachine(memory : Buffer[Int], inputQueue : Seq[Int] = Seq[Int]()) {
  import scala.collection.mutable.ListBuffer

  def runCalculation() : Seq[Int] = {
    // var memory = new VirtualMachine(input.toBuffer)
    while (step) {}
    // allMemory
    outputQueue.toSeq
  }

  val outputQueue = ListBuffer[Int]()
  var inputQueueTop = 0

  var pc = 0

  def incPC(inc : Int = 1) : Unit = pc += inc

  def value(address : Int, mode : ParameterMode.Value = ParameterMode.Position) : Int = {
      val retv = mode match {
          case ParameterMode.Position => memory(memory(address))
          case ParameterMode.Immediate => memory(address)
      }
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
      retv
  }

  val opcode = Instructions.values.toList.map(x => (x.opcode, x.code)).toMap
  val parameters = Instructions.values.toList.map(x => (x.opcode, x.paramCount)).toMap

  object Instructions extends Enumeration {
    protected case class Val(opcode : Int, paramCount : Int, code : (Seq[ParameterMode.Value]) => Boolean) extends super.Val 

    import scala.language.implicitConversions
    implicit def valueToInstructionVal(x: Value): Val = x.asInstanceOf[Val]

    val Add = Val(1, 3, (p) => {
      setValue(value(pc + 3, ParameterMode.Immediate), value(pc + 1, p(0)) + value(pc + 2, p(1)))
      true
    })
    val Multiply = Val(2, 3, (p) => {
      setValue(value(pc + 3, ParameterMode.Immediate), value(pc + 1, p(0)) * value(pc + 2, p(1)))
      true
    })
    val IntegerInput = Val(3, 1, (p) => {
        // println("Enter integer: ")
        val num = inputQueue(inputQueueTop)
        inputQueueTop += 1
        setValue(value(pc + 1, ParameterMode.Immediate), num)
        true
    })
    val IntegerOutput = Val(4, 1, (p) => {
        // println("Diagnostic: " + value(pc + 1, p(0)) + " @ memory: " + pc)
        outputQueue += value(pc + 1, p(0))
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

def convertToList(input : String) : Array[Int] = input.split(",").map(_.toInt)

val testData = Seq(
    ("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0","43210"),
    ("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0","54321"),
    ("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0","65210")
    )

def performRun(code : Array[Int], inputPhases : Seq[Int]) : Int = {
  var input = 0
  inputPhases.foreach(p => {
    val vm = new VirtualMachine(code.toBuffer, Seq(p,input))
    input = vm.runCalculation.head
  })
  input
}

import scala.collection.mutable.ArrayBuffer
val permutations = ArrayBuffer[Seq[Int]]()
heapPermutations(Buffer(0,1,2,3,4), 5)

def checkPermutations(c : String) : Int = {
    val code = convertToList(c)
    var bestResult = 0
    var bestInput = Seq[Int]()
    permutations.foreach (input => {
      val a = performRun(code, input)
      if (a > bestResult) {
          bestResult = a
          bestInput = input
      }
    })
    bestResult
}

testData.foreach({ case (i,o) => {
    val bestResult = checkPermutations(i)
    if (bestResult.toString != o) {
        println(s"Expecting $o but got $bestResult for $i")
    }
}})

// Part 1
val inputFile = io.Source.fromFile("input.txt")
val input1 = try inputFile
                   .getLines
                   .next
             finally inputFile.close()
println("Part1:")
println(checkPermutations(input1))

// I know Scala has a built-in permutations method, but I wanted to
// implement my own
// https://en.wikipedia.org/wiki/Heap%27s_algorithm
def heapPermutations(a : Buffer[Int], size : Int) : Unit = {
    if (size == 1) {
        permutations += a.toSeq
    }
    // val input = a.toBuffer
    for (i <- 0 until size) {
        heapPermutations(a, size - 1)

        if ((size % 2) == 1) {
            val temp = a(0)
            a(0) = a(size - 1)
            a(size-1) = temp
        }
        else {
            val temp = a(i)
            a(i) = a(size-1)
            a(size-1) = temp
        }
    }
}
