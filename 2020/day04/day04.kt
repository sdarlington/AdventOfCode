
import java.io.File

enum class PassportField (val key: String) {
    BIRTH_YEAR("byr"),
    ISSUE_YEAR("iyr"),
    EXPIRATION_YEAR("eyr"),
    HEIGHT("hgt"),
    HAIR_COLOR("hcl"),
    EYE_COLOR("ecl"),
    PASSPORT_ID("pid"),
    COUNTRY_ID("cid")
}

class Passport (val fields: Map<String,String>) {
    fun validate() : Boolean {
        val req = setOf("byr","iyr","eyr","hgt","hcl","ecl","pid")
        val cur = fields.keys.toSet()
        return cur.containsAll(req)
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
}