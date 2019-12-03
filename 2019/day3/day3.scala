

// Test data
val testData = Seq(
  ("R8,U5,L5,D3","U7,R6,D4,L4",6),
  ("R75,D30,R83,U83,L12,D49,R71,U7,L72","U62,R66,U55,R34,D71,R55,D58,R83",159),
  ("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51","U98,R91,D20,R16,D67,R40,U7,R15,U6,R7",135)
)

object Direction extends Enumeration {
    type Direction = Value
    val U, D, R, L = Value
}
case class Vector (direction : Direction.Value, distance : Int)

def parsePath(path : String) : Array[Vector] = {
    path.split(",").map(d => {
        val direction = d.charAt(0) match {
            case 'R' => Direction.R
            case 'L' => Direction.L
            case 'U' => Direction.U
            case 'D' => Direction.D
        }
        val distance = d.substring(1).toInt
        Vector(direction, distance)
    })
}

import scala.collection.mutable.SortedSet

def generateLocationMap(path : Array[Vector]) : SortedSet[(Int,Int)] = {
    val map = SortedSet[(Int,Int)]()
    var location = (0,0)
    path.foreach(p => {
        for (i <- 1 to p.distance) {
            val (x,y) = location
            location = p.direction match {
                case Direction.U => (x, y + 1)
                case Direction.D => (x, y - 1)
                case Direction.L => (x - 1, y)
                case Direction.R => (x + 1, y)
            }
            map += location
        }
    })
    map
}

def calculateDistance(p1 : String, p2 : String) : Int = {
  val path1 = parsePath(p1)
  val path2 = parsePath(p2)
  val map1 = generateLocationMap(path1)
  val map2 = generateLocationMap(path2)
  map1.intersect(map2)
      .filter({ case (x,y) => x != 0 && y != 0 })
      .map({ case (x,y) => math.abs(x) + math.abs(y) })
      .min
}

testData.foreach({ case (p1,p2,a) =>
  val manhattanDistance = calculateDistance(p1,p2)
  if (manhattanDistance != a) {
    println(s"Failed for $p1 / $p2. Got $manhattanDistance. Expected $a.")
  }
})

val inputFile = io.Source.fromFile("input.txt")
val input1 = inputFile.getLines

val path1 = input1.next
val path2 = input1.next

inputFile.close()

val distance = calculateDistance(path1,path2)

println(distance)
