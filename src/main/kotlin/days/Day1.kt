package days

class Day1 : Day(1) {

    override fun partOne(): Any {
        val ints = inputList.map { it.toInt() }
        val pairs = ints
                .flatMap { e1 -> ints.map { e2 -> Pair(e1, e2) } }
        return pairs
                .filter { it.first + it.second == 2020 }
                .map { it.first * it.second }
                .first()
    }

    override fun partTwo(): Any {
        val ints = inputList.map { it.toInt() }
        val pairs = ints.flatMap { e1 -> ints
                .flatMap { e2 ->
                    ints.map { e3 -> Triple(e1, e2, e3)} } }
        return pairs.asSequence()
                .filter { it.first + it.second + it.third == 2020 }
                .map { it.first * it.second * it.third }
                .first()
    }
}


