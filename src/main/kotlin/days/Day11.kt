package days

import days.Cell.EMPTY
import days.Cell.FLOOR
import days.Cell.OCCUPIED

typealias NextCell = (Pair<Int, Int>, Cell, List<List<Cell>>) -> Cell

class Day11 : Day(11) {

    override fun partOne(): Any {
        val input = prepareInput(inputList)
        //printRoad(input)
        val board = live(input, ::nextCellAdjacent)
        return board.flatten().filter { it == OCCUPIED }.size
    }

    override fun partTwo(): Any {

        val input = prepareInput(inputList)
        //printRoad(input)
        val board = live(input, ::nextCellSeen)
        return board.flatten().filter { it == OCCUPIED }.size
    }

    var c = 0;
    private fun live(current: List<List<Cell>>, nextCell: NextCell): List<List<Cell>> {
        val next = current.mapIndexed { y, row ->
            row.mapIndexed { x, it ->
                nextCell(x to y, it, current)
            }
        }
//         printRoad(next)
//        if (c++ == 3) {
//            return next
//        }
        return if (next == current) current else live(next, nextCell)
    }

    private fun nextCellAdjacent(point: Pair<Int, Int>, current: Cell, board: List<List<Cell>>) = when (current) {
        FLOOR -> current
        EMPTY -> if (adjacentCells(point, board).any { it == OCCUPIED }) EMPTY else OCCUPIED
        OCCUPIED -> if (adjacentCells(point, board).filter { it == OCCUPIED }.size >= 4) EMPTY else OCCUPIED
    }

    private fun nextCellSeen(point: Pair<Int, Int>, current: Cell, board: List<List<Cell>>) = when (current) {
        FLOOR -> current
        EMPTY -> if (seenCells(point, board).any { it == OCCUPIED }) EMPTY else OCCUPIED
        OCCUPIED -> if (seenCells(point, board).filter { it == OCCUPIED }.size >= 5) EMPTY else OCCUPIED
    }

    private fun adjacentCells(point: Pair<Int, Int>, board: List<List<Cell>>): List<Cell> =
            adjacentPositions(point)
                    .map { board.getOrNull(it.second)?.getOrNull(it.first) ?: FLOOR }


    private fun seenCells(point: Pair<Int, Int>, board: List<List<Cell>>): List<Cell> =
            directions().map { seenCell(point, board, it) }

    private fun seenCell(point: Pair<Int, Int>, board: List<List<Cell>>, direction: Pair<Int, Int>): Cell {
        val nextPoint: Pair<Int, Int> = point + direction
        val (nextX, nextY) = nextPoint
        val nextCell = board.getOrNull(nextY)?.getOrNull(nextX)
        return when (nextCell) {
            null -> FLOOR
            OCCUPIED, EMPTY -> nextCell
            FLOOR -> seenCell(nextPoint, board, direction)
        }
    }

    private fun adjacentPositions(point: Pair<Int, Int>): List<Pair<Int, Int>> = directions().map {
        it.plus(point)
    }

    private fun directions(): List<Pair<Int, Int>> = listOf(
            Pair(-1, -1), Pair(0, -1), Pair(1, -1),
            Pair(-1, 0), Pair(1, 0),
            Pair(-1, 1), Pair(0, 1), Pair(1, 1)
    )

    private fun printRoad(input: List<List<Cell>>) {
        println("BOARD")
        input.forEach { println(it) }
    }

    private fun prepareInput(list: List<String>): List<List<Cell>> = list.map { l ->
        l.map {
            when (it) {
                '.' -> FLOOR
                'L' -> EMPTY
                else -> throw error(it)
            }
        }
    }


}

private operator fun Pair<Int, Int>.plus(another: Pair<Int, Int>): Pair<Int, Int> {
    return first + another.first to second + another.second
}

enum class Cell { FLOOR, EMPTY, OCCUPIED }
