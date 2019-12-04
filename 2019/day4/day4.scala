

// Test data
val testData = Seq(
    (111111, true),
    (223450, false),
    (123789, false)
)

testData.foreach({
    case (d,a) => if (testNumber1(d) != a) { println (s"Failed: $d. Expected $a.") }
})

def testNumber1(d : Int) : Boolean = adjacentDigits(d) && increasingDigits(d)
def testNumber2(d : Int) : Boolean = consecutiveDigits(d) && increasingDigits(d)

def adjacentDigits(d : Int) : Boolean = {
    var pass = false
    var last = -1
    d.toString.foreach(c => {
        if (!pass) {
            pass = (c == last)
        }
        last = c
    })
    pass
}

def consecutiveDigits(d : Int) : Boolean = {
    var pass = false
    var count = 0
    var last = '-'
    // TODO: hacky but working :(
    d.toString.foreach(c => {
        if (!pass) {
            if (c == last) {
                count += 1
            }
            else {
                pass = (count == 2)
                count = 1
            }
        }
        last = c
    })
    if (!pass && count == 2) {
        pass = true
    }
    pass
}

def increasingDigits(d : Int) : Boolean = {
    var pass = true
    var last = -1
    d.toString.foreach(c => {
        pass = pass && (c >= last)
        last = c
    })
    pass
}

val input = "124075-580769"
val range = input.split("-").map(_.toInt)
val r = Range(range(0), range(1))

var count1 = 0
var count2 = 0
for (i <- range(0) to range(1)) {
    if (testNumber1(i)) {
        count1 += 1
    }
    if (testNumber2(i)) {
        count2 += 1
    }
}
println (count1)
println (count2)
