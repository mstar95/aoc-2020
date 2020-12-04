package days

class Day2 : Day(2) {

    override fun partOne(): Any {
        val input = inputList.map { splitFirst(it) }
        return input.filter { run(it)  }.size
    }

    override fun partTwo(): Any {
        val input = inputList.map { splitSecond(it) }
        return input.filter { runSecond(it)  }.size
    }

    fun splitFirst(r: String): Triple<Times, String, String> {
        val splits = r.split(' ')
        val first = splits[0].split("-")
        val times = Times(first[0].toInt(), first[1].toInt())
        val value = splits[1].dropLast(1)
        val rest = splits[2]
        return Triple(times, value, rest)
    }

    fun splitSecond(r: String): Triple<Positions, String, String> {
        val splits = r.split(' ')
        val first = splits[0].split("-")
        val times = Positions(first[0].toInt(), first[1].toInt())
        val value = splits[1].dropLast(1)
        val rest = splits[2]
        return Triple(times, value, rest)
    }

    fun run( triple: Triple<Times, String, String>): Boolean {
        val(  times, value, rest )= triple

        val filter = rest.toList()
                .filter { it.toString() == value }
        val length = filter
                .size
        return times.from <= length && times.to >= length
    }

    private fun runSecond(triple: Triple<Positions, String, String>): Boolean {
        val (pos, value, rest) = triple
        return (rest[pos.first- 1].toString() == value).xor(rest[pos.second -1 ].toString() == value)
    }
}

data class Times(val from: Int, val to: Int)
data class Positions(val first: Int, val second: Int)
