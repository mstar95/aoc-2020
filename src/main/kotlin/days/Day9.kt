package days


class Day9 : Day(9) {

    override fun partOne(): Any {
        val input = prepareInput(inputList)
        val result = calculate(input, 25)
        return result
    }

    private fun calculate(input: List<Long>, log: Int): Long {
        return (log until input.size).asSequence()
                .map { findSum(input.subList(it - log, it), input[it]) }
                .find { it != null } ?: -1
    }

    private fun findSum(numbers: List<Long>, sum: Long): Long? {
        println(" ${numbers.size}, $numbers, $sum")
        val find = pairs(numbers).asSequence()
                .map { (a, b) -> a + b }
                .find { it == sum }
        return if (find == null) sum else null
    }

    private fun pairs(list: List<Long>): List<Pair<Long, Long>> = list.flatMap { e1 -> list.map { e2 -> Pair(e1, e2) } }

    override fun partTwo(): Any {
        val v1 = 127L
        val v2 = 18272118L
        val input = prepareInput(inputList)
        val set = findContiguousSum(input, v2)
        val min = set.minOrNull()!!
        val max = set.maxOrNull()!!
        print(set)
        print(min)
        println(max)
        return min + max
    }

    private fun findContiguousSum(inputList: List<Long>, objective: Long): Set<Long> {
        var start = 0
        var end = 1
        var sum = inputList[start] + inputList[end]
        while (sum != objective) {
            if (start == end) {
                throw RuntimeException()
            }
            if (sum > objective) {
                sum -= inputList[start]
                start++
            }
            if (sum < objective) {
                end++
                sum += inputList[end]
            }
        }
        return inputList.subList(start, end).toSet()
    }
}

private fun prepareInput(list: List<String>) = list.map { it.toLong() }

