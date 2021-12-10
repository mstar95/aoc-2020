package `2021`

import days.Day
import java.lang.Integer.max
import java.lang.Integer.min

class Day5 : Day(5) {

    override fun partOne(): Any {
        val lanes = inputList.map {
            it.split(" -> ")
                .map { it.split(",").map { it.toInt() } }
                .map { Point(it[0], it[1]) }
        }.map { Line(it[0], it[1]) }
        val simple = lanes.filter { it.a.x == it.b.x || it.a.y == it.b.y }
        val xd = simple.flatMap { it.draw() }.groupBy { it }.mapValues { it.value.size }.filter { it.value > 1 }
        return xd.count()
    }

    override fun partTwo(): Any {
        val lanes = inputList.map {
            it.split(" -> ")
                .map { it.split(",").map { it.toInt() } }
                .map { Point(it[0], it[1]) }
        }.map { Line(it[0], it[1]) }
        val xd = lanes.flatMap { it.draw() }.groupBy { it }.mapValues { it.value.size }.filter { it.value > 1 }
        return xd.count()
    }

    fun product(x: Point, y: Point, z: Point): Int {
        val x1: Int = z.x - x.x
        val y1: Int = z.y - x.y
        val x2: Int = y.x - x.x
        val y2: Int = y.y - x.y
        return x1 * y2 - x2 * y1
    }

    fun sameLine(x: Point, y: Point, z: Point): Boolean {
        return min(x.x, y.x) <= x.x && x.x <= max(x.x, y.x) && min(
            x.y,
            y.y
        ) <= z.y && z.y <= max(x.y, y.y)
    }

    fun cross(A: Point, B: Point, C: Point, D: Point): Boolean {
        val v1: Int = product(C, D, A)
        val v2: Int = product(C, D, B)
        val v3: Int = product(A, B, C)
        val v4: Int = product(A, B, D)


        if ((v1 > 0 && v2 < 0 || v1 < 0 && v2 > 0) && (v3 > 0 && v4 < 0 || v3 < 0 && v4 > 0)) return true

        if (v1 == 0 && sameLine(C, D, A)) return true
        if (v2 == 0 && sameLine(C, D, B)) return true
        if (v3 == 0 && sameLine(A, B, C)) return true
        return (v4 == 0 && sameLine(A, B, D))

    }

    data class Point(val x: Int, val y: Int)
    data class Line(val a: Point, val b: Point) {

        private fun downToUp(x: Int, y: Int): IntProgression = if (x < y) (x..y) else (x downTo y)

        fun draw() = if (a.x == b.x || a.y == b.y) drawHorizotnal() else draw45()

        fun drawHorizotnal() = downToUp(a.x, b.x).flatMap { x ->
            downToUp(a.y, b.y).map { y ->
                Point(x, y)
            }
        }

        fun draw45() = downToUp(a.x, b.x).zip(downToUp(a.y, b.y)) { a, b -> Point(a, b) }
    }
}

