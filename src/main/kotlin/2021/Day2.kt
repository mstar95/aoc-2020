package `2021`

import days.Day

class Day2 : Day(2) {

    override fun partOne(): Any {
        val pairs = inputList.map { it.split(" ").let { it[0] to it[1].toInt() } }
        val p = Pos(0,0)
        pairs.forEach {
            when (it.first) {
                 "forward" -> p.w = p.w + it.second
                 "up" -> p.h = p.h - it.second
                 "down" -> p.h = p.h + it.second
            }
        }
        return p.h * p.w
    }

    override fun partTwo(): Any {
        val pairs = inputList.map { it.split(" ").let { it[0] to it[1].toInt() } }
        val p = Pos(0,0)
        var aim = 0
        pairs.forEach {
            println("$p, $aim")
            when (it.first) {
                "forward" -> {
                    p.w += it.second
                    p.h += (it.second * aim)
                }
                "up" -> aim -= it.second
                "down" -> aim += it.second
            }
        }
        return p.h * p.w
    }
}

data class Pos(var h: Int, var w: Int)