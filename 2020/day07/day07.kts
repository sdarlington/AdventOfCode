
import java.io.File

class Bag(val count : Int, val adjective : String, val colour : String) {
    fun matchingBag(bag : Bag) : Boolean = this.adjective == bag.adjective && this.colour == bag.colour

    override fun toString() : String = "Bag[$count $adjective $colour]"
}

fun Bag(desc : String) : Bag {
    val components = desc.split(" ")
    if (components.count() == 3) {
        return Bag(components[0].toInt(), components[1], components[2])
    }
    else {
        return Bag(1, components[0], components[1])
    }
}

class BagRule(val bag : Bag, val contains : List<Bag>) {
    override fun toString() : String = "BagRule[$bag -> $contains]"
}

fun processRule(rule : String) : BagRule {
    val ruleComponents = rule.replace(Regex(" bags?\\.?"), "")
                             .split (" contain ")

    val firstBag = Bag(ruleComponents[0])

    val containTextRules = ruleComponents.last().split(", ")
                                         .filter { x -> x != "no other" }
    val contains = containTextRules.map { x -> Bag(x) }

    return BagRule(firstBag, contains)
}

fun readRules(file : String) : List<BagRule> {
    return File(file).readLines()
                     .map { x -> processRule(x) }

}

fun matchBagsFrom(start : Bag, rules : List<BagRule>) : List<Bag> {
    val matchingBags : MutableList<Bag> = mutableListOf()

    val matches = rules.filter { x -> x.contains.filter { y -> y.matchingBag(start) }.count() > 0 }
                       .map { x -> x.bag }
    matchingBags.addAll(matches)

    for (b in matches) {
        matchingBags.addAll(matchBagsFrom(b,rules))
    }

    return matchingBags
}

fun countBags(start: Bag, rules: List<BagRule>) : Int {
    val thisBag = rules.filter { x -> x.bag.matchingBag(start) }.first()

    val subBags = thisBag.contains.map { x -> countBags(x, rules) }
    val subCount = if (subBags.count() == 0) 0 else subBags.reduce { acc, v -> acc + v }

    return start.count + start.count * subCount
}

// sample
println ("Sample")
val sampleRules = readRules("example.txt")
val sampleMatch = matchBagsFrom(Bag(1,"shiny", "gold"), sampleRules).toSet()
println (sampleMatch.count())

// step 1
println ("Step 1")
val inputRules = readRules("input.txt")
val step1Match = matchBagsFrom(Bag(1,"shiny", "gold"), inputRules).toSet()
println (step1Match.count())

// Step 2
println ("Step 2")
val step2sample = countBags(Bag(1,"shiny", "gold"), sampleRules) - 1
println (step2sample)
val step2 = countBags(Bag(1,"shiny", "gold"), inputRules) - 1
println (step2)
