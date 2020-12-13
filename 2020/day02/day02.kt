
import java.io.File

class Password(val minCount : Int, val maxCount : Int, val letter : Char, val password : String) 

fun checkPassword(password : Password) : Boolean {
    val check = password.password.filter { x -> x == password.letter }.count()
    return (check >= password.minCount && check <= password.maxCount)
}

fun checkPassword2(password : Password) : Boolean {
    val p1 = password.password.getOrElse(password.minCount - 1) { '.' }
    val p2 = password.password.getOrElse(password.maxCount - 1) { '.' }
    return ((p1 == password.letter).xor(p2 == password.letter))
}

fun parseInput(line : String) : Password {
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
        print (p)
        print (" -> ")
        println(checkPassword(p))
    }

    // Part 1
    val entries = File("input.txt").readLines()
                                   .map { x -> parseInput(x) }
                                   .map { x -> checkPassword(x) }
                                   .filter { x -> x == true }
                                   .count()
    println (entries)

    // Part 2
    val entries2 = File("input.txt").readLines()
                                    .map { x -> parseInput(x) }
                                    .map { x -> checkPassword2(x) }
                                    .filter { x -> x == true }
                                    .count()
    println (entries2)
}