package days

import util.groupByEmpty

class Day4 : Day(4) {

    override fun partOne(): Any {
        val props = getProps()
        val valid = props.filter { isValid(it.keys) }
        return valid.size
    }

    private fun getProps(): List<Map<Key, String>> {
        val groups = groupByEmpty(inputList)
        println(groups)
        println(groups.toString() == "[[eyr:1972 cid:100, hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926], [iyr:2019, hcl:#602927 eyr:1967 hgt:170cm, ecl:grn pid:012533040 byr:1946], [hcl:dab227 iyr:2012, ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277], [hgt:59cm ecl:zzz, eyr:2038 hcl:74454a iyr:2023, pid:3556412378 byr:2007], [pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980, hcl:#623a2f], [eyr:2029 ecl:blu cid:129 byr:1989, iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm], [hcl:#888785, hgt:164cm byr:2001 iyr:2015 cid:88, pid:545766238 ecl:hzl, eyr:2022], [iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719]]")
        val flatten = flatten(groups)
        val props = flatten.map { props(it) }
        return props
    }


    override fun partTwo(): Any {
        val props = getProps()
        val valid = props.filter { isValid(it.keys) }
        val result = valid.filter { validateValues(it) }
        return result.size
    }

    private fun flatten(groups: List<List<String>>): List<List<String>> {
        return groups.map { group -> group.flatMap { it.split(" ") } }
    }

    private fun props(input: List<String>): Map<Key, String> {
        return input.map { it.split(":") }
                .map { Key.valueOf(it[0]) to it[1] }.toMap()
    }

    private fun isValid(keys: Set<Key>): Boolean {
        if (keys.size == Key.values().size) {
            return true
        }
        if (keys.size == Key.values().size - 1 && !keys.contains(Key.cid)) {
            return true
        }
        return false
    }

    private fun validateValues(input: Map<Key, String>): Boolean {
        val debug = false
        if(debug == true) {
            val x = input.entries
                    .map { it to validateEntry(it) }
            if(x.any{ !it.second }) {
                println(x)
            }
            return x.map { it.second }.all { it }

        }
        return input.entries.all { validateEntry(it) }
    }

    private fun validateEntry(entry: Map.Entry<Key, String>) = when (entry.key) {
        Key.byr -> entry.value.toInt().let { it in 1920..2002 }
        Key.iyr -> entry.value.toInt().let { it in 2010..2020 }
        Key.eyr -> entry.value.toInt().let { it in 2020..2030 }
        Key.hgt -> hgt(entry.value)
        Key.hcl -> hcl(entry.value)
        Key.ecl -> entry.value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        Key.pid -> pid(entry)
        Key.cid -> true
    }

    private fun pid(entry: Map.Entry<Key, String>) =
            entry.value.length == 9 && entry.value.all { it.isDigit() }

    private fun hgt(input: String): Boolean {
        return when(input.takeLast(2)) {
            "cm" -> input.dropLast(2).toInt().let { it in 150..193 }
            "in" -> input.dropLast(2).toInt().let { it in 59..76 }
            else -> false
        }
    }

    private fun hcl(input: String): Boolean {
        if (input[0].toString() == "#" ) {
            val rest = input.drop(1)
            if(rest.length == 6) {
                return rest.all { it.toString().matches(Regex("[0-9]|[a-f]")) }
            }
        }
        return false
    }
}

enum class Key { byr, iyr, eyr, hgt, hcl, ecl, pid, cid }
