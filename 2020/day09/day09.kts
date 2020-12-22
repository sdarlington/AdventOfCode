

import java.io.File



class Window(val stream: List<Long>, val preamble : Int) {
    override fun toString() = "Window[$preamble $stream"

    fun checkPosition(posn : Int) : Boolean {
        if (posn < preamble) {
            return false
        }

        val window = stream.subList(posn - preamble, posn)
        val target = stream[posn]
        var found = false
        for (n in window) {
            if (window.filter { x -> x + n == target}.count() == 1) {
                found = true
                break
            }
        }
        return found
    }

    fun checkWindow() : Long {
        for (p in (preamble .. stream.count() - 1)) {
            if (!checkPosition(p)) {
                return stream[p]
            }
        }
        return -1
    }

    fun sumTo(target : Long, posn : Int) : Int {
        var count = 0L
        for (p in (posn .. stream.count() - 1)) {
            count += stream[p]
            if (count == target) {
                return p
            }
            else if (count > target) {
                return -1
            }
        }
        return -1
    }

    fun findSum(target : Long) : List<Long> {
        for (p in (0 .. stream.count() - 1)) {
            val result = sumTo(target, p)
            if (result > 0) {
                return stream.subList(p, result + 1)
            }
        }
        return listOf()
    }
}
fun Window(filename: String, preamble: Int) : Window {
    val stream = File(filename).readLines()
                               .map { x -> x.toLong() }
    return Window(stream, preamble)
}

println ("Sample")
val w = Window("example.txt",5)
println (w.checkWindow())

println ("Part 1")
val w1 = Window("input.txt",25)
println (w1.checkWindow())

println ("Part 2 (example)")
val rangeExample = w.findSum(127).sortedWith(compareBy { it })
val exResult = rangeExample.first() + rangeExample.last()
println ("$exResult")

println ("Part 2 (for realz)")
val range = w1.findSum(29221323).sortedWith(compareBy { it })
val p2Result = range.first() + range.last()
println ("$p2Result")
