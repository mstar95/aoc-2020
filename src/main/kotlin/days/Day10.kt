package days

import java.text.DecimalFormat


class Day10 : Day(10) {

    override fun partOne(): Any {
        val input = prepareInput(inputList)
        val sorted = input.sorted()
        val result = calculateDifferences(0, sorted, Differences(0, 0, 0))
        println(result)
        return result.one * result.three
    }

    override fun partTwo(): Any {
        val input = prepareInput(inputList)
        val sorted = input.sorted()
        val result = calculateCombinations(0, sorted)
        println(0.3.toFloat().toDouble().toFloat())
        return result
    }

    private fun calculateCombinations(jolt: Int, jolts: List<Int>): Long {
        if (jolts.size == 0) {
            return 1
        }
        val v1 = jolts.first()
        val v2 = jolts.getOrNull(1)
        val v3 = jolts.getOrNull(2)
        val v4 = jolts.getOrNull(3)
        val d1 = v1 - jolt
        val d2 = minus(v2, jolt)
        val d3 = minus(v3, jolt)
        val d4 = minus(v4, jolt)
//        println( "V: $v1, $v2, $v3, $v4")
//        println( "D: $d1, $d2, $d3, $d4")
        if(d1 != 1 || v2 == null) {
            return calculateCombinations(v1, jolts.drop(1))
        }
        if(d2 != 2 || v3 == null) {
            return calculateCombinations(v1, jolts.drop(1))
        }

        if(d3 != 3 || v4 == null) {
            return calculateCombinations(v2, jolts.drop(2)) * 2
        }

        if(d4 != 4 ) {
            return calculateCombinations(v3  , jolts.drop(3)) * 4

        }
        return calculateCombinations( v4, jolts.drop(4)) * 7

    }

    private fun minus(v2: Int?, jolt: Int) = v2?.let { it - jolt } ?: 0

    fun calculateDifferences(jolt: Int, jolts: List<Int>, differences: Differences): Differences {
        if (jolts.isEmpty()) {
            return differences.put(3)
        }
        val head = jolts.first()

        return calculateDifferences(head, jolts.tail(), differences.put(head - jolt))
    }
}

data class Differences(val one: Int, val two: Int, val three: Int) {
    fun put(diff: Int): Differences {
        return when (diff) {
            1 -> copy(one = one + 1)
            2 -> copy(two = two + 1)
            3 -> copy(three = three + 1)
            else -> throw error("Unsupported diff: $diff")
        }
    }
}

private fun <T> List<T>.tail() = drop(1)

private fun prepareInput(list: List<String>) = list.map { it.toInt() }

