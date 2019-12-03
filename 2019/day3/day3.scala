

// Test data
val testData = Seq(
  ("R8,U5,L5,D3","U7,R6,D4,L4",Calculation.Manhattan,6),
  ("R75,D30,R83,U83,L12,D49,R71,U7,L72","U62,R66,U55,R34,D71,R55,D58,R83",Calculation.Manhattan,159),
  ("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51","U98,R91,D20,R16,D67,R40,U7,R15,U6,R7",Calculation.Manhattan,135),
  ("R8,U5,L5,D3","U7,R6,D4,L4",Calculation.Shortest,30),
  ("R75,D30,R83,U83,L12,D49,R71,U7,L72","U62,R66,U55,R34,D71,R55,D58,R83",Calculation.Shortest,610),
  ("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51","U98,R91,D20,R16,D67,R40,U7,R15,U6,R7",Calculation.Shortest,410)
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

import scala.collection.mutable.HashMap

def generateLocationMap(path : Array[Vector]) : HashMap[(Int,Int),Int] = {
    val map = HashMap[(Int,Int), Int]()
    var location = (0,0)
    var totalDistance = 0
    path.foreach(p => {
        for (i <- 1 to p.distance) {
            val (x,y) = location
            location = p.direction match {
                case Direction.U => (x, y + 1)
                case Direction.D => (x, y - 1)
                case Direction.L => (x - 1, y)
                case Direction.R => (x + 1, y)
            }
            totalDistance += 1
            map += (location -> totalDistance)
        }
    })
    map
}

object Calculation extends Enumeration {
    type Calculation = Value
    val Manhattan, Shortest = Value
}

def calculateDistance(p1 : String, p2 : String, calculation : Calculation.Value) : Int = {
  val path1 = parsePath(p1)
  val path2 = parsePath(p2)
  val map1 = generateLocationMap(path1)
  val map2 = generateLocationMap(path2)
  // FIXME: can't get the type signatures right for this... works but ugly :(
  val f = calculation match {
      case Calculation.Manhattan => x : (Int,Int) => x match { case (x, y) =>  math.abs(x) + math.abs(y) }
      case Calculation.Shortest => x : (Int,Int)  => map1(x) + map2(x)
  }
  map1.keys.toSet.intersect(map2.keys.toSet)
         .map(f)
         .min
}

testData.foreach({ case (p1,p2,f,a) =>
  val distance = calculateDistance(p1,p2,f)
  if (distance != a) {
    println(s"Failed for $p1 / $p2. Got $distance. Expected $a.")
  }
})

val inputFile = io.Source.fromFile("input.txt")
val input1 = inputFile.getLines

val path1 = input1.next
val path2 = input1.next

inputFile.close()

val manhattan = calculateDistance(path1,path2, Calculation.Manhattan)
val shortest = calculateDistance(path1,path2, Calculation.Shortest)

println(s"Part 1: $manhattan\nPart 2: $shortest")
