

case class Position(x : Int, y : Int, z : Int)
case class Velocity(x : Int, y : Int, z : Int)


// parseFile("input.txt")
// <x=-1, y=0, z=2>
// <x=2, y=-10, z=-7>
// <x=4, y=-8, z=8>
// <x=3, y=5, z=-1>

import scala.collection.mutable.ListBuffer

class PlanetSystem(positions : ListBuffer[Position]) {
  val velocities = ListBuffer[Velocity]()

  for (i <- 0 until positions.size) {
      velocities += Velocity(0,0,0)
  }

  def positions() : Seq[Position] = positions.toSeq

  def applyGravity(p1 : Int, p2 : Int, v1 : Int, v2 : Int) : (Int,Int) = {
      if (p1 == p2) {
          (v1,v2)
      }
      else if (p1 > p2) {
        (v1 - 1, v2 + 1)
      }
      else {
          (v1 + 1, v2 - 1)
      }
  }

  def calculateGravity() : Unit = {
    (0 until positions.size).toSeq.combinations(2).foreach(p => {
        val planet1 = positions(p(0))
        val planet2 = positions(p(1))
        val velocity1 = velocities(p(0))
        val velocity2 = velocities(p(1))

        val x = applyGravity(planet1.x,planet2.x,velocity1.x,velocity2.x)
        val y = applyGravity(planet1.y,planet2.y,velocity1.y,velocity2.y)
        val z = applyGravity(planet1.z,planet2.z,velocity1.z,velocity2.z)

        val newVel1 = Velocity(x._1,y._1,z._1)
        val newVel2 = Velocity(x._2,y._2,z._2)

        velocities(p(0)) = newVel1
        velocities(p(1)) = newVel2
    })
  }

  def calulatePosition() : Unit = {
      for (i <- 0 until positions.size) {
          val p = positions(i)
          val v = velocities(i)
        positions(i) = Position(p.x + v.x, p.y + v.y, p.z + v.z)
      }
  }

import math.abs
  def calulateEnergy : Int = {
    positions.zip(velocities).map({
        case (p,v) =>
          (abs(p.x) + abs(p.y) + abs(p.z)) *
          (abs(v.x) + abs(v.y) + abs(v.z))
    })
    .reduce(_+_)
  }

  def step() : Unit = {
    calculateGravity
    calulatePosition
  }

  def output() : Unit = {
      println("***")
      for (i <- 0 until positions.size) {
          println(s"pos=<x=${positions(i).x}, y=  ${positions(i).y}, z= ${positions(i).z}>, vel=<x= ${velocities(i).x}, y= ${velocities(i).y}, z= ${velocities(i).z}>")
      }
  }
}

val positions = ListBuffer(
    Position(-1,0,2),
    Position(2,-10,-7),
    Position(4,-8,8),
    Position(3,5,-1)
)

val system = new PlanetSystem(positions)
system.output
for (i <- 0 until 10) {
    system.step
    system.output
}
println (system.calulateEnergy)

def parseFile(name : String) : List[Position] = {
  val inputFile = io.Source.fromFile(name)
  val input1 = try inputFile
                   .getLines
                   .toList
             finally inputFile.close()

  val pattern = "<x=(-?[0-9]+).*y=(-?[0-9]+).*z=(-?[0-9]+)>".r
  input1.map( l => {
    val pattern(x,y,z) = l
    Position(x.toInt,y.toInt,z.toInt)
  })
}

println("Part 1")
val in = parseFile("input.txt")
val mutableIn = ListBuffer[Position]()
in.foreach(mutableIn += _)
val planets = new PlanetSystem(mutableIn)
for (i <- 0 until 1000) {
    planets.step
    // planets.output
}
println(planets.calulateEnergy)

println("Part 2")
import scala.collection.mutable.Set
val planets2 = new PlanetSystem(mutableIn)
val oldPositions = Set[Seq[Position]]()
var iter = 0
while (!oldPositions.contains(planets2.positions())) {
    oldPositions += planets2.positions()
    planets2.step
    iter += 1
    if (iter % 1000000 == 0) { println (s"... $iter")}
}
println(iter)
