
import java.io.File

class Password(val minCount : Int, val maxCount : Int, val letter : Char, val password : String) {
    fun checkPassword() : Boolean {
        val check = this.password.filter { x -> x == this.letter }.count()
        return (check >= this.minCount && check <= this.maxCount)
    }

    fun checkPassword2() : Boolean {
        val p1 = this.password.getOrElse(this.minCount - 1) { '.' }
        val p2 = this.password.getOrElse(this.maxCount - 1) { '.' }
        return ((p1 == this.letter).xor(p2 == this.letter))
    }

    override fun toString() : String = "Password[minCount:$minCount,maxCount:$maxCount,letter:$letter,password:$password]"
}

fun Password(line : String) : Password {
    val components = line.split(" ")
    val counts = components.getOrElse(0) { "0-0" }
    val letter = components.getOrElse(1) { ":" }
    val password = components.getOrElse(2) { " " }

    val mixMax = counts.split("-").map { x -> x.toInt() }

    return Password(mixMax.first(), mixMax.last(), letter.first(), password.trim() )
}

fun main() {
    // Example
    val example = listOf(Password(1,3,'a',"abcde"), Password(1,3,'b',"cdefg"), Password(2,9,'c',"ccccccccc"))
    for (p in example) {
        println ("$p -> ${p.checkPassword()}")
    }

    // Part 1
    val entries = File("input.txt").readLines()
                                   .map { x -> Password(x) }
                                   .map { x -> x.checkPassword() }
                                   .filter { x -> x == true }
                                   .count()
    println (entries)

    // Part 2
    val entries2 = File("input.txt").readLines()
                                    .map { x -> Password(x) }
                                    .map { x -> x.checkPassword2() }
                                    .filter { x -> x == true }
                                    .count()
    println (entries2)
}