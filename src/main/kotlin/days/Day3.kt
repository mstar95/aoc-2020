package days

class Day3 : Day(3) {

    override fun partOne(): Any {
        return solve(1, 3)
    }

    override fun partTwo(): Any {
        val listOf = listOf(
                solve(1, 1),
                solve(1, 3),
                solve(1, 5),
                solve(1, 7),
                solve(2, 1))
       // print(listOf)
        return listOf
               .fold( 1L, {i1, i2 -> i1 * i2})
    }

    private fun solve(down: Int, right: Int): Int {
        return inputList.mapIndexed { id, it -> if (id % down == 0) it.getOrNull(id * right / down % it.length) else null }
                .filter { it.toString() == HASH }
                .size
    }

    companion object {
        const val HASH = "#"
    }
}

