package `2021`

import days.Day

class Day1 : Day(1) {

    override fun partOne(): Any {
        val ints = inputList.map { it.toInt() }
        print(ints)
        return ints.fold(0 to null as Int?) { acc, it ->
            if(acc.second?.let { i -> i < it } == true) {
                acc.first + 1 to it
            } else {
                acc.first to it
            }
        }.first ?: 0
    }

    override fun partTwo(): Any {
        val ints = inputList.map { it.toInt() }
        val w = Window(ints[0], ints[1], ints[2])
        return ints.drop(3).fold(0 to w) { acc, it ->
            print(acc.second)
            val w2 = acc.second.new(it)
            if(acc.second.sum < w2.sum) {
                acc.first + 1 to w2
            } else {
                acc.first to w2
            }
        }.first
    }
}

data class Window(val a: Int, val b: Int, val c: Int) {
    val sum = a + b + c
    fun new(value: Int) = Window(b, c, value)
}