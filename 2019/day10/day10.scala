

val testData = Seq("example1.txt","example2.txt","example3.txt","example4.txt")

import scala.collection.mutable.TreeSet
def readMap(map : Seq[String]) : TreeSet[(Int,Int)] = {
    val asteroids = TreeSet[(Int,Int)]()
    map.zipWithIndex.foreach({ case (r,y) => 
      r.zipWithIndex.foreach({ case (c, x) => 
        if (c == '#') {
          asteroids += ((x, y))
        }
      })
    })
    asteroids
}

def clearView(point1 : (Int,Int), point2 : (Int,Int), others : TreeSet[(Int,Int)]) : Boolean = {
    val (ax,ay) = (point1._1.toDouble + 0.5, point1._2.toDouble + 0.5)
    val (bx,by) =  (point2._1.toDouble + 0.5, point2._2.toDouble + 0.5)
    val distance = others.toList
                         .map({ case (x,y) => (x.toDouble + 0.5, y.toDouble + 0.5)})
                         .map({ case (x,y) => 
                                val dxc = x - ax
                                val dyc = y - ay

                                val dxl = bx - ax
                                val dyl = by - ay

                               val cross = dxc * dyl - dyc * dxl

                               if (cross == 0) {
                                 if (math.abs(dxl) >= math.abs(dyl))
                                   if (dxl > 0) {
                                     ax <= x && x <= bx
                                   }
                                   else {
                                     bx <= x && x <= ax;
                                   }
                                 else
                                   if (dyl > 0) {
                                     ay <= y && y <= by
                                   }
                                   else {
                                     by <= y && y <= ay;                               }
                                   }
                               else {
                                   false
                               }
                             })
    distance.filter(_ == true).size == 0
}

def viewCount(point : (Int,Int), others: TreeSet[(Int,Int)]) : Int = {
    val views = others.toList.map(x => {
                                 clearView(point,x,others &~ TreeSet(x))
                               })
    
    val clearViews = views.filter(_ == true)
    val c = clearViews.size
    c
}

def checkMap(points : TreeSet[(Int,Int)]) : Unit = {
    var bestAsteroid = (0,0)
    var bestCount = 0
    points.foreach(x => {
        val vc = viewCount(x, points &~ TreeSet(x))
        if (vc > bestCount) {
            bestCount = vc
            bestAsteroid = x
        }
    })
    println (s"Best asteroid: $bestAsteroid with $bestCount")
}

testData.foreach(x => {
  val inputFile = io.Source.fromFile(x)
  val input1 = try inputFile
                   .getLines
                   .toSeq
             finally inputFile.close()

  val asteroids = readMap(input1)
//   checkMap(asteroids)
})

// Part 1
println("Part 1")
  val inputFile = io.Source.fromFile("input.txt")
  val input1 = try inputFile
                   .getLines
                   .toSeq
             finally inputFile.close()

  val asteroids = readMap(input1)
//   checkMap(asteroids)

println("Part 2")
val station = (22,19)
val targets = asteroids &~ TreeSet(station)

import scala.collection.mutable.ListBuffer
val angles = targets.toList.map({ case (x,y) => 
                  val dx = x - station._1
                  val dy = y - station._2

                  val len = dx*dx + dy*dx

                  var angle = math.toDegrees(math.atan2(dx, dy)) - 90.0
                  if (angle < 0) {
                      angle += 360.0
                  }
                // var angle = math.atan2(dy, dx)

                  (angle,len,(x,y))
                })
                .sortWith((x,y) => { if (x._1 == y._1) { x._2 < y._2 } else { x._1 < y._1 } })
                // .toBuffer
// println (angles)
// println (angles.head)
// println (angles.last)

// var angleSet = TreeSet[(Double,Int,(Int,Int))]() &~ angles
// angleSet += angles
var zapped = TreeSet[(Int,Int)]()
while (zapped.size < angles.size){
    var lastZapped = -1.0
    angles.foreach({ case (a,d,c) =>
        if (!zapped.contains(c)) {
            if (a != lastZapped) {
                zapped += c
                if (zapped.size == 200) {
                    println (s"This is the 200th: $c")
                }
                lastZapped = a
            }
        }
    })
}

// not 807
// not 1405
// not 313 (too low)
// not 1806
// not 718