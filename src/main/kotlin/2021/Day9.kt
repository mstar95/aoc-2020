package `2021`

import days.Day

class Day9 : Day(9) {

    override fun partOne(): Any {
        val board = createBoard(inputList)
        return lowPoints(board).sumOf { it.second + 1 }
    }


    override fun partTwo(): Any {
        val board = createBoard(inputList)
        val basins = lowPoints(board).map {
            findBasin(board, listOf(it))
        }.map {
            it.size
        }.sortedDescending()
      //  println(basins)
        return basins[0] * basins[1] * basins[2]
    }


    fun findBasin(
        board: Board,
        toSee: List<Pair<Point, Int>>,
        seen: Set<Point> = setOf(),
    ): Set<Point> {
        if (toSee.isEmpty()) {
            return seen;
        }
        val f = toSee.first()
        val n = board.neighbours(f.first).filter { it.second != 9 }.filter { it.first !in seen }
            .filter { it.second > f.second }
        return findBasin(board, toSee.drop(1) + n, seen + f.first)
    }

    fun lowPoints(board: Board): List<Pair<Point, Int>> {
        return board.board.filter { point ->
            val neighbours = board.neighboursValues(point.key)
            val all = neighbours.all { it > point.value }
            all
        }.map { it.key to it.value }
    }

    fun createBoard(param: List<String>): Board {
        val mutableMap = mutableMapOf<Point, Int>()
        var x = 0
        var y = 0
        for (line in param) {
            for (c in line) {
                val p = Point(x, y)
                mutableMap.put(p, c.toString().toInt())
                x++
            }
            x = 0
            y++
        }

        return Board(mutableMap.toMap())
    }

    data class Board(val board: Map<Point, Int> = mapOf()) {
        val sides = listOf(
            // 1 to 1,
            //  1 to -1,
            1 to 0,
            0 to 1,
            0 to -1,
            // -1 to 1,
            // -1 to -1,
            -1 to 0,
        )

        fun neighboursValues(p: Point): List<Int> = sides.map {
            Point(it.first + p.x, it.second + p.y)
        }.map {
            board[it] ?: Int.MAX_VALUE
        }

        fun neighbours(p: Point): List<Pair<Point, Int>> = sides.map {
            Point(it.first + p.x, it.second + p.y)
        }.mapNotNull { i ->
            board[i]?.let { i to it }
        }
    }

    data class Point(val x: Int, val y: Int)
}

