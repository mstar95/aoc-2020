package `2021`

import days.Day
import java.lang.Integer.min

class Day7 : Day(7) {

    override fun partOne(): Any {
        val crabs = inputString.split(",").map { it.toInt() }.sorted()
        val crabsPos = crabs.groupBy { it }.mapValues { it.value.size }
        val end = crabs.last()
        val start = crabs.first()
        var fuel = calculateFuel(crabs, start)
        var seen = 0
        var unseen = crabs.size
        (start..end).forEach {
            seen += crabsPos.getOrDefault(it, 0)
            unseen -= crabsPos.getOrDefault(it, 0)
            val newFuel = fuel + seen - unseen
            if (newFuel > fuel) {
                return fuel
            }
            fuel = newFuel
        }
        return 0
    }

    private fun calculateFuel(crabs: List<Int>, start: Int): Int {
        var fuel = 0
        crabs.forEach {
            if (start < it) {
                fuel += it - start
            }
        }
        return fuel
    }

    override fun partTwo(): Any {
        val crabs = inputString.split(",").map { it.toInt() }.sorted()
        val end = crabs.last()
        val start = crabs.first()

        return divideAndConquer(start, end, crabs)
    }

    private fun calculateFuel2(crabs: List<Int>, pos: Int): Int {
        var fuel = 0
        crabs.forEach {
            if (pos < it) {
                fuel += sum(it - pos)
            }
            if (pos > it) {
                fuel += sum(pos - it)
            }
        }
        return fuel
    }

    fun divideAndConquer(start: Int, end: Int, crabs: List<Int>): Int {
        if(end - start == 1) {
            return min(calculateFuel2(crabs, start), calculateFuel2(crabs, end))
        }
        val half = start + ((end - start) / 2)
        val half2 = half + 1
        val r1 = calculateFuel2(crabs, half)
        val r2 = calculateFuel2(crabs, half2)
        if (r1 > r2) {
            return divideAndConquer(half2, end, crabs)
        }
        if (r1 < r2) {
            return divideAndConquer(start, half, crabs)
        }
        return r1
    }

    fun sum(last: Int) = ((1 + last)) * (last) / 2

}

