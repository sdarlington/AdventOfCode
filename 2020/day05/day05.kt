import java.io.File

fun calculateSeat(ticket: String) : Pair<Int,Int> {
    val bits = ticket.map { x -> when (x) { 'F' -> "0"; 'B' -> "1"; 'L' -> "0"; 'R' -> "1"; else -> "-1" } }
                    .map { x -> x.toInt() }
    val rowEntries = bits.subList(0,7)
    val seatEntries = bits.subList(7,10)

    val row = rowEntries.zip(listOf(64,32,16,8,4,2,1))
                        .map { x -> x.first * x.second }
                        .reduce { x,y -> x + y }
    val seat = seatEntries.zip(listOf(4,2,1))
                        .map { x -> x.first * x.second }
                        .reduce { x,y -> x + y }
    return Pair(row,seat)
}

fun calculateSeatID(seat: Pair<Int,Int>) : Int = seat.first * 8 + seat.second

fun main() {

    // Examples
    for (ticket in listOf("FBFBBFFRLR","BFFFBBFRRR","FFFBBBFRRR","BBFFBBFRLL")) {
        println (calculateSeatID(calculateSeat(ticket)))
    }

    // Part 1
    val part1 = File("input.txt").readLines()
                                 .map { x -> calculateSeatID(calculateSeat(x)) }
                                 .reduce { acc, v -> if (v > acc) v else acc }
    println (part1)
}