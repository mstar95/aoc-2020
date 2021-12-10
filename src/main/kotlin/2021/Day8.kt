package `2021`

import days.Day

class Day8 : Day(8) {

    val unique = listOf(2, 4, 3, 7)

    val numbers = mapOf(
        0 to listOf(1, 2, 3, 4, 5, 6),
        1 to listOf(2, 3),
        2 to listOf(1, 2, 4, 5, 7),
        3 to listOf(1, 2, 3, 4, 7),
        4 to listOf(2, 3, 6, 7),
        5 to listOf(1, 3, 4, 6, 7),
        6 to listOf(1, 3, 4, 5, 6, 7),
        7 to listOf(1, 2, 3),
        8 to listOf(1, 2, 3, 4, 5, 6, 7),
        9 to listOf(1, 2, 3, 4, 6, 7),
    )

    val XD = numbers.map { it.value to it.key }.toMap()

    override fun partOne(): Any {
        val lines = inputList.map {
            it.split("|")
                .get(1).split(" ").filter { !it.isBlank() }
        }
        return lines.map { it.filter { it.length in unique }.size }.sum()
    }


    override fun partTwo(): Any {
        val out = inputList.map {
            it.split("|")
                .get(1).split(" ").filter { !it.isBlank() }
        }
        val input = inputList.map {
            it.split("|")
                .get(0).split(" ").filter { !it.isBlank() }
        }

        val digits: Map<Int, Char?> = (1..7).map { it to (null as Char?) }.toMap()
        val digitsRes: List<Map<Char, Int>> =
            input.map { explore(it, digits) }.map {
                it.map {
                    it.value!! to it.key
                }.toMap()
            }
        return digitsRes.zip(out).map { t: Pair<Map<Char, Int>, List<String>> ->
            val l: List<String> = t.second.map { s ->
                val xd: List<Int> = s.map { c -> t.first[c]!! }.sorted()
                XD[xd]!!.toString()
            }
            val toInt = l.fold("") { a, b -> a + b }.toInt()
            toInt
        }.sum()
    }

    fun explore(list: List<String>, digits: Map<Int, Char?>): Map<Int, Char?> {
        val uni = list.filter { it.length in unique }.groupBy { it.length }.mapValues { it.value.first() }
        val one = uni[2]!!
        val seven = uni[3]!!
        val pos1 = seven.filter { !one.contains(it) }.first()
        val withPos1: Map<Int, Char?> = digits + (1 to pos1)
        val firstTry: Map<Int, Char?> = withPos1 + (2 to one.first()) + (3 to one[1])
        val secondTry: Map<Int, Char?> = withPos1 + (3 to one.first()) + (2 to one[1])
        val r1 = exploreMore(list, firstTry)
        if (r1 == null) {
            return exploreMore(list, secondTry)!!
        }
        return r1
    }

    fun exploreMore(list: List<String>, digits: Map<Int, Char?>): Map<Int, Char?>? {

        numbers.forEach { n: Map.Entry<Int, List<Int>> ->
            var OK = false
            list.forEach { d: String ->
                if (d.length == n.value.size) {
                    var ok = true
                    n.value.forEach {
                        val d1 = digits[it]
                        if (d1 == null) {
                            d.forEach { i ->
                                if (i !in digits.values.filterNotNull()) {
                                    val e = exploreMore(list, digits + (it to i))
                                    if (e != null) {
                                        return e
                                    }
                                }
                            }
                            ok = false // O TU BRAKOWALO

                        } else {
                            if (!d.contains(d1)) {
                                ok = false
                            }
                        }
                    }
                    if (ok) {
                        OK = true
                    }
                }
            }
            if (!OK) {
                return null
            }
        }
        return digits
    }
}

