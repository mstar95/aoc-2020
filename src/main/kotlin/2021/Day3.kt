package `2021`

import days.Day

class Day3 : Day(3) {

    override fun partOne(): Any {
        val pairs = inputList.map { it.split("").drop(1).dropLast(1).map { it.toInt() } }
        val accumulator = pairs.first().map { 0 }.toMutableList()
        pairs.forEach { row ->
            row.forEachIndexed { index, i -> accumulator[index] += i }
        }
        val half = pairs.size / 2
        val gamma = accumulator.map { if (it > half) 1 else 0 }.joinToString(separator = "") { it.toString() }.toInt(2)
        val epsilon =
            accumulator.map { if (it > half) 0 else 1 }.joinToString(separator = "") { it.toString() }.toInt(2)
        return gamma * epsilon
    }

    override fun partTwo(): Any {
        val pairs: List<List<Int>> = inputList.map { it.split("").drop(1).dropLast(1).map { it.toInt() } }
        val a = iterate(pairs, 0, 1).joinToString(separator = "") { it.toString() }.toInt(2)
        val b = iterate(pairs,0 ,0).joinToString(separator = "") { it.toString() }.toInt(2)
        return a*b
    }

    fun iterate(l: List<List<Int>>, i: Int, bit: Int): List<Int> {
        println(l.size)
        if (l.size == 1) {
            return l.first()
        }
        val score = l.fold(0) { acc, it -> if(it[i] == bit) acc + 1 else acc }
        val other =  l.size - score
        if(bit == 1 ) {
            return if (score >= other) {
                val rest = l.filter { it[i] == bit }
                iterate(rest, i + 1, bit)
            } else {
                val rest = l.filter { it[i] != bit }
                iterate(rest, i + 1, bit)
            }
        } else {
            return if (score <= other) {
                val rest = l.filter { it[i] == bit }
                iterate(rest, i + 1, bit)
            } else {
                val rest = l.filter { it[i] != bit }
                iterate(rest, i + 1, bit)
            }
        }
    }
}

