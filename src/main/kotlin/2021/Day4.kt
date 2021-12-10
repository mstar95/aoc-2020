package `2021`

import days.Day

class Day4 : Day(4) {

    override fun partOne(): Any {
        val numbers: List<Int> = inputList.first().split(",").map { it.toInt() }
        val boards: List<Board> = createBoards(inputList.drop(2))
        val bingo = mutableSetOf<Int>()
        val (wonBoard, wonNumber) = playBingo(bingo, numbers, boards)
        return wonBoard.mutableSet.map { it.value }.filter { !bingo.contains(it) }.sum() * wonNumber
    }

    override fun partTwo(): Any {
        val numbers: List<Int> = inputList.first().split(",").map { it.toInt() }
        val boards: List<Board> = createBoards(inputList.drop(2))
        val bingo = mutableSetOf<Int>()
        val (wonBoard, wonNumber) = playLostBingo(bingo, numbers, boards.toMutableList())
        return wonBoard.mutableSet.map { it.value }.filter { !bingo.contains(it) }.sum() * wonNumber
    }

    fun playBingo(bingo: MutableSet<Int>, numbers: List<Int>, boards: List<Board>): Pair<Board, Int> {
        numbers.forEach { number ->
            bingo.add(number)
            boards.forEach { board ->
                if (isBingo(board.rows, bingo) || isBingo(board.columns, bingo)) return board to number
            }
        }
        throw IllegalStateException()
    }

    fun playLostBingo(bingo: MutableSet<Int>, numbers: List<Int>, boards: MutableList<Board>): Pair<Board, Int> {
        numbers.forEach { number ->
            bingo.add(number)
            val won = boards.filter { board ->
                isBingo(board.rows, bingo) || isBingo(board.columns, bingo)
            }
            boards.removeAll(won)
            if (boards.isEmpty()) {
                assert(won.size == 1)
                return won.first() to number
            }
        }
        throw IllegalStateException()
    }

    fun isBingo(rows: Map<Int, List<Int>>, bingo: MutableSet<Int>): Boolean {
        return rows.values.any { row ->
            row.all {
                bingo.contains(it)
            }
        }
    }

    fun createBoards(param: List<String>): List<Board> {
        val boards = mutableListOf<Board>()
        var x = 0
        var y = 0
        var board = Board()
        for (line in param) {
            if (line.isBlank()) {
                y = 0
                boards.add(board)
                board = Board()
                continue
            }
            line.split(" ").filter { it.isNotBlank() }.forEach {
                val p = Point(x, y, it.toInt())
                board.add(p)
                x++
            }
            x = 0
            y++
        }
        boards.add(board)
        return boards.toList()
    }

    data class Board(val mutableSet: MutableSet<Point> = mutableSetOf()) {
        fun add(p: Point) {
            mutableSet.add(p)

        }

        val rows: Map<Int, List<Int>> by lazy {
            mutableSet.groupBy({ it.x }, { it.value })
        }

        val columns: Map<Int, List<Int>> by lazy {
            mutableSet.groupBy({ it.y }, { it.value })
        }

    }

    data class Point(val x: Int, val y: Int, val value: Int)
}

