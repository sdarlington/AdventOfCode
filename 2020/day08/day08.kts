

import java.io.File

class OpCode(val code : String, val argument : Int) {
    override fun toString() = "OpCode[$code $argument]"
}
fun OpCode(text : String) : OpCode {
    val components = text.split(" ")
    return OpCode(components.getOrElse(0) { "" }, components.getOrElse(1) { "0" }.toInt())
}

class Interpreter(val filename : String) {
    var code : List<OpCode> = listOf()
    var acc = 0
    var pc = 0
    val seen : MutableSet<Int> = mutableSetOf()

    fun readCode() {
        code = File(filename).readLines()
                                  .map { x -> OpCode(x) }
    }

    fun printCurrentLine() {
        println (this.code.getOrElse(this.pc) { OpCode("inv", -1) } )
    }

    fun execute() : Boolean {
        var end = false
        if (seen.contains(pc)) {
            println ("Accumulator: $acc")
            end = true
        }
        else {
            seen.add(pc)
        }
        val instruction = code.getOrElse(pc) { OpCode("nop", 0) }
        when (instruction.code) {
            "acc" -> acc += instruction.argument
            "jmp" -> pc += instruction.argument - 1
            // else -> 
        }
        pc++

        return end
    }

    fun run() {
        var go = true
        while (go) {
            printCurrentLine()
            go = !execute()
        }
    }
}

// Sample
val si = Interpreter("example.txt")
si.readCode()
si.run()

// Part 1
println ("Part 1")
val p1i = Interpreter("input.txt")
p1i.readCode()
p1i.run()