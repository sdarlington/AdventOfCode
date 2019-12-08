

case class Image (data : String, x : Int, y : Int) {
    def point(x : Int, y: Int, layer : Int) : Int = {
      data( layer * this.x * this.y + y * this.x + x ) - '0'
    }

    def layer(l : Int) : Seq[Int] = data.slice(l * x * y, l * x * y + x * y).map { _.toInt - '0' }

    def layerCount : Int = data.length / (x * y)

    def colourAtPoint(x : Int, y : Int) : Int = {
        val p = for (l <- 0 until layerCount) yield point(x,y,l)
        p.filter(_ != 2).head
    }

    def colourImage : Seq[Int] = {
        for (y <- 0 until this.y; x <- 0 until this.x) yield colourAtPoint(x,y)
    }

    def displayColouredImage : Unit = {
        val img = colourImage.map({
            case 1=> "⬜"*2
            case 0 => "⬛"*2
            case 2 => "?"*2
        })
        for (row <- 0 until this.y) {
          println(img.slice(row * x, row * x + x).mkString)
          println(img.slice(row * x, row * x + x).mkString)
        }
    }
}

val inputFile = io.Source.fromFile("input.txt")
val input1 = try inputFile
                   .getLines
                   .next
             finally inputFile.close()

val image = Image(input1, 25,6)


val layers = for(i <- 0 until image.layerCount) yield (i, image.layer(i).filter(_ == 0).size)

val layersSorted = layers.sortWith(_._2 < _._2)
val layer = image.layer(layersSorted.head._1)
val calc = layer.filter(_ == 1).size * layer.filter(_ == 2).size

println(s"Part 1\n$calc")

println("Part 2")
image.displayColouredImage
