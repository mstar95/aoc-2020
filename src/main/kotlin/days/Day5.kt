package days

class Day5 : Day(5) {

    override fun partOne(): Any {
        val ids: List<Int> = inputList.map { toInt(it) }

        print(ids)

        return ids.maxOrNull() ?: -1
    }

    override fun partTwo(): Any {
        val ids: List<Int> = inputList.map { toInt(it) }
        val result = (1..850).filter { !ids.contains(it) }
        println(result)
        return 0;
    }


    private fun toInt(row: String) = row.map { toChar(it) }
            .joinToString(separator = "")
            .let { Integer.parseInt(it, 2) }

    private fun toChar(it: Char) = when (it) {
        'B', 'R' -> '1'
        'F', 'L' -> '0'
        else -> throw RuntimeException("Bad input $it")
    }

}