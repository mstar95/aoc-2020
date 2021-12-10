package days


class Day17 : Day(17) {

    override fun partOne(): Any {
        val input = countActiveAfter(1, 3, inputList)
        println(input)
        return input
    }

    override fun partTwo(): Any {
        val input = countActiveAfter(6, 4, inputList)
        return input
    }

    data class DimensionalCube(private val coordinates: List<Int>) {

        fun neighbors(): List<DimensionalCube> {
            return recurse(listOf())
        }

        fun recurse(prefix: List<Int>): List<DimensionalCube> {
            val position = prefix.size
            if (position == coordinates.size) {
                if (prefix != coordinates) { // cube isn't a neighbor to itself
                    return listOf(DimensionalCube(prefix))
                }
            } else {
                return (coordinates[position] - 1..coordinates[position] + 1).flatMap { recurse(prefix + it) }
            }
            return listOf()
        }
    }


    fun countActiveAfter(rounds: Int, dimensions: Int, input: List<String>): Int {
        var prevActive: Set<DimensionalCube> = input.withIndex().flatMap { (y, line) ->
            line.toCharArray().withIndex().filter { it.value == '#' }.map { it.index }
                    .map { x -> DimensionalCube(listOf(x, y, *Array(dimensions - 2) { 0 })) }
        }.toSet()
        repeat(rounds) {
            // Active cells remain active with 2 or 3 neighbors, inactive cells become active with 3
            prevActive =
                    prevActive.flatMap(DimensionalCube::neighbors).groupingBy { it }.eachCount()
                            .filter { (cube, count) -> count == 3 || (count == 2 && cube in prevActive) }.keys
        }
        return prevActive.size
    }
}
