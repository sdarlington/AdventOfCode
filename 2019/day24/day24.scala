


import scala.collection.mutable.HashSet

class Grid(sx : Int, sy : Int) {
  val input =  HashSet[(Int,Int)]()

  def addPoint(x : (Int,Int)) = input += x
  def addPoints(x : Seq[(Int,Int)]) = input ++= x
  def isBug(x : Int, y : Int) : Boolean = input.contains((x,y))

  def bugCount(x : Int, y : Int) : Int = {
      Seq(
           (x, y-1), (x-1,y), (x+1,y), (x, y+1)
          )
          .map(x => { isBug(x._1, x._2) })
          .filter(_ == true)
          .size
  }

  def nextState : Grid = {
      val newGrid = new Grid(sx,sy)
      for (y <- 0 until sy; x <- 0 until sx) {
          val c = bugCount(x,y)
          if (input.contains((x,y))) {
            if (c == 1) {
              newGrid.addPoint((x,y))
            }
          }
          else {
              if (c == 1 || c == 2) {
                  newGrid.addPoint((x,y))
              }
          }
      }
      newGrid
  }

  def output = {
      println(input)
      for (y <- 0 until sy) {
          for (x <- 0 until sx) {
              print (if (input.contains((x,y))) { '#' } else { '.' } )
          }
          println()
      }
  }

  def rating = {
    input.map(x => { math.pow(2, x._1 + x._2 * sx).toInt }).reduce(_+_)
  }

  def canEqual(a: Any) = a.isInstanceOf[Grid]
  override def equals(that: Any): Boolean =
        that match {
            case that: Grid => {
                that.canEqual(this) &&
                this.input == that.input
            }
            case _ => false
        }
  override def hashCode: Int = input.hashCode

}

def firstMatch(input : Grid) : Int = {
  var grid  = input
  val grids = new HashSet[Grid]()
  while (!grids.contains(grid)) {
    grids += grid
    grid = grid.nextState
  }
  grid.output
  grid.rating
}

val grid = new Grid(5,5)
grid.addPoints(Seq(
    (4,0),
    (0,1),
    (3,1),
    (0,2),
    (3,2),
    (4,2),
    (2,3),
    (0,4)))

println(firstMatch(grid))

// println()
println ("Part 1")
val inputFile = io.Source.fromFile("input.txt")
val input1 = try inputFile
                   .getLines
                   .toSeq
             finally inputFile.close()
var grid1 = new Grid(5,5)
input1.zipWithIndex.foreach(_ match {
    case (v,y) => v.zipWithIndex.foreach(_ match {
        case (v, x) => if (v == '#') {
            grid1.addPoint((x,y))
        }
    })
})
    
grid1.output
println(firstMatch(grid1))
