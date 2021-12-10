package days


class Day15 : Day(15) {

    override fun partOne(): Any {
        val input = prepareInput(inputString)
        println(input)
        val map = prepareMap(input)
        val result = play(map, 2020, input.last(), (input.size).toLong())
        return result
    }

    override fun partTwo(): Any {
        val input = prepareInput(inputString)
        println(input)
        val map = prepareMap(input)
        val result = play(map, 30000000, input.last(), (input.size).toLong())
        return result
    }

    private tailrec fun play(numbers: MutableMap<Long, Long>, iterations: Long, lastNumber: Long, lastIteration: Long): Long {
        if (lastIteration == iterations) {
            return lastNumber
        }
        val last = numbers.getOrDefault(lastNumber, null)
        if (last == null) {
            numbers.put(lastNumber, lastIteration)
            return play(numbers , iterations, 0, lastIteration + 1)
        }
        val nextNumber = lastIteration - last
        numbers.put(lastNumber, lastIteration)
        return play(numbers , iterations, nextNumber, lastIteration + 1)
    }

    private fun prepareInput(input: String): List<Long> = input.split(',').map { it.toLong() }

    private fun prepareMap(list: List<Long>) = list.dropLast(1).mapIndexed { idx, it -> it to (idx + 1).toLong() }.toMap().toMutableMap()

}

