
import java.io.File

fun intRange(text : String, min : Int, max: Int) : Boolean {
    val x = text.toInt()
    return x >= min && x <=max
}

enum class PassportField (val key: String, val validation: (String) -> Boolean) {
    BIRTH_YEAR("byr", { x -> intRange(x, 1920, 2002) }),
    ISSUE_YEAR("iyr", { x -> intRange(x, 2010, 2020)}),
    EXPIRATION_YEAR("eyr", { x -> intRange(x, 2020, 2030 )}),
    HEIGHT("hgt", { x -> true }),
    HAIR_COLOR("hcl", { x -> true }),
    EYE_COLOR("ecl", { x -> true }),
    PASSPORT_ID("pid", { x -> true }),
    COUNTRY_ID("cid", { x -> true })
}

class Passport (val fields: Map<String,String>) {
    fun validate() : Boolean {
        val req = PassportField.values().map { x -> x.key }.toSet()
        val cur = fields.keys.toSet()
        return cur.containsAll(req)
    }

    fun validate2() : Boolean {
        val step1 = validate()

        var step2 = true
        for (f in fields) {
            val field = PassportField.values().filter { x -> x.key == f.key }.first()
            val valid = field.validation(f.value)
            step2 = step2 && valid
        }

        return step1 && step2
    }

    override fun toString() : String {
        return fields.toString()
    }
}

fun read(file: String) : List<Passport> {
    val entries = File(file).readLines()
    var passports : MutableList<Passport> = mutableListOf()

    var fields : MutableMap<String,String> = mutableMapOf()
    for (line in entries) {
        val keyvalues = line.split(" ").filter { x -> x != "" }
        if (keyvalues.count() == 0) {
            passports.add(Passport(fields))
            fields = mutableMapOf()
        }
        else {
            for (kv in keyvalues) {
                val kandv = kv.split(":")
                fields[kandv.first()] = kandv.last()
            }
        }
    }
    passports.add(Passport(fields))

    return passports
}

fun main() {
  val examples = read("example.txt")
  val exampleCount = examples.map { x -> x.validate() }
                             .filter { x -> x == true}
                             .count()
  println(exampleCount)

  // step 1
  val exercise = read("input.txt")
  val step1 = exercise.map { x -> x.validate() }
                      .filter { x -> x == true }
                      .count()
  println(step1)

  // step 2
  val example2Count = examples.map { x -> x.validate2() }
  println(example2Count)
}