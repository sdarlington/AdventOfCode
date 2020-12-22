

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
        val instruction = code.getOrElse(pc) { OpCode("end", 0) }
        when (instruction.code) {
            "acc" -> acc += instruction.argument
            "jmp" -> pc += instruction.argument - 1
            "end" -> end = true
            // else -> 
        }
        pc++

        return end
    }

    fun run() : Boolean {
        pc = 0
        acc = 0
        seen.clear()
        var go = true
        while (go) {
            printCurrentLine()
            go = !execute()
        }
        return (pc > code.count())
    }

    fun permutations() : Int {
        val originalCode = code
        val editableCode = code.toMutableList()
        for (idx in (0..code.count()-1)) {
            println ("Permutation $idx")
            if (code[idx].code == "nop") {
                editableCode[idx] = OpCode("jmp", code[idx].argument)
            }
            else if (code[idx].code == "jmp") {
                editableCode[idx] = OpCode("nop", code[idx].argument)
            }
            else {
                continue
            }
            code = editableCode
            if (run()) {
                return acc
            }
            editableCode[idx] = originalCode[idx]
        }
        return 0
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

// Part 2
println ("Part 2 (sample)")
si.permutations()
println ("Part 2 (input)")
p1i.permutations()
