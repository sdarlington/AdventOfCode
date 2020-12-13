import scala.collection.mutable.Buffer
import scala.collection.mutable.ArrayBuffer

class VirtualMachine(memory : Buffer[Long]) {
  import scala.collection.mutable.ListBuffer

  def this(input : String) = this(input.split(",").map(_.toLong).toBuffer)

  def runCalculation(stopOnOutput : Boolean = false) : Seq[Long] = {
    // var memory = new VirtualMachine(input.toBuffer)
    while (step && (!stopOnOutput || (stopOnOutput && outputQueue.size == 0))) {}
    // allMemory
    val retv = outputQueue.toSeq
    outputQueue.clear
    retv
  }

  val outputQueue = ListBuffer[Long]()
  val inputQueue = ArrayBuffer[Long]()

  var pc = 0
  var relativeOffsetBase = 0

  def incPC(inc : Int = 1) : Unit = pc += inc

  def mapAddress(address : Int, mode : ParameterMode.Value) : Int = {
      mode match {
          case ParameterMode.Position => memory(address).toInt
          case ParameterMode.Immediate => address
          case ParameterMode.Relative => relativeOffsetBase + memory(address).toInt
      }
  }
  def value(address : Int, mode : ParameterMode.Value = ParameterMode.Position) : Long = {
      val addr = mapAddress(address,mode)
      if (addr >= memory.size) {
          memory.padToInPlace(addr + 10, 0)
      }
      memory(addr)
  }

  def setValue(address : Int, value : Long) : Unit = {
      if (address >= memory.size) {
          memory.padToInPlace(address + 10, 0)
      }
      memory(address) = value
    }

  def allMemory() : Array[Long] = memory.toArray[Long]

  def printMemory() : Unit = println(memory.mkString(","))

  def printCommands() : Unit = {
      var c = 0
      while (c < 200) {
          val command = value(c, ParameterMode.Immediate)
        val (instruction,modes) = instructionToOpcodeParameterMode(command)
        print (s"$c  $instruction ($command) ")
        for (i <- 1 to parameters(instruction).toInt) {
            print (s" ${value(c+i,ParameterMode.Immediate)} ")
        }
        println()
        c += parameters(instruction).toInt + 1
      }
  }

  object ParameterMode extends Enumeration {
      type ParameterMode = Value
      val Position, Immediate, Relative = Value
  }

  def instructionToOpcodeParameterMode(instruction : Long) : (Long,Seq[ParameterMode.Value]) = {
    val opcode = if (instruction < 100) { instruction  } else { instruction % 100 }
    val modeCount = parameters(opcode)
    val modesList = (instruction / 100)
                   .toString
                   .reverse
    val modes = modesList
                   .concat("0" * (modeCount.toInt - modesList.length))
                   .map(_ match {
                       case '0' => ParameterMode.Position
                       case '1' => ParameterMode.Immediate
                       case '2' => ParameterMode.Relative
                   })
    (opcode,modes)
  }

  def step() : Boolean = {
      val (instruction,modes) = instructionToOpcodeParameterMode(value(pc, ParameterMode.Immediate))
      val retv = opcode(instruction)(modes)
      incPC(parameters(instruction).toInt + 1)
      retv
  }

  val opcode = Instructions.values.toList.map(x => (x.opcode, x.code)).toMap
  val parameters = Instructions.values.toList.map(x => (x.opcode, x.paramCount)).toMap

  object Instructions extends Enumeration {
    protected case class Val(opcode : Long, paramCount : Long, code : (Seq[ParameterMode.Value]) => Boolean) extends super.Val 

    import scala.language.implicitConversions
    implicit def valueToInstructionVal(x: Value): Val = x.asInstanceOf[Val]

    val Add = Val(1, 3, (p) => {
      setValue(mapAddress(pc + 3, p(2)), value(pc + 1, p(0)) + value(pc + 2, p(1)))
      true
    })
    val Multiply = Val(2, 3, (p) => {
      setValue(mapAddress(pc + 3, p(2)), value(pc + 1, p(0)) * value(pc + 2, p(1)))
      true
    })
    val IntegerInput = Val(3, 1, (p) => {
        // println("Enter integer: ")
        val num = inputQueue.head
        inputQueue.remove(0)
        setValue(mapAddress(pc + 1, p(0)), num)
        true
    })
    val LongegerOutput = Val(4, 1, (p) => {
        outputQueue += value(pc + 1, p(0))
        true
    })
    val JumpIfTrue = Val(5, 2, (p) => {
        if (value(pc + 1, p(0)) != 0) {
            // PC will be incremented by 3 on returm
            pc = value(pc + 2, p(1)).toInt - 3
        }
        true
    })
    val JumpIfFalse = Val(6,2, (p) => {
        if (value(pc + 1, p(0)) == 0) {
            pc = value(pc + 2, p(1)).toInt - 3
        }
        true
    })
    val LessThan = Val(7, 3, (p) => {
        setValue(mapAddress(pc + 3, p(2)),
          if (value(pc + 1, p(0)) < value(pc + 2, p(1))) {
            1
          }
          else {
              0
          })
          true
    })
    val Equals = Val(8, 3, (p) => {
        setValue(mapAddress(pc + 3, p(2)).toInt,
          if (value(pc + 1, p(0)) == value(pc + 2, p(1))) {
            1
          }
          else {
              0
          })
          true
    })
    val UpdateRelativeOffset = Val(9, 1, (p) => {
        relativeOffsetBase += value(pc + 1, p(0)).toInt
        true
    })
    val Halt = Val(99, 0, (p) => false)
  }

}
