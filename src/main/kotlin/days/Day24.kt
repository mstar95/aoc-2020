package days

import arrow.core.mapOf
import days.Direction.E
import days.Direction.NE
import days.Direction.NW
import days.Direction.SE
import days.Direction.SW
import days.Direction.W
import days.HexColor.*

class Day24 : Day(24) {

    override fun partOne(): Any {
        val input = parseInput(inputList)
        println(run(listOf(E, SE, NE, E)))
        val result = input.fold(mapOf(), this::run)
        return result.filter { it.value == BLACK }.size
    }

    override fun partTwo(): Any {
        val input = parseInput(inputList)
        val floor = input.fold(mapOf(), this::run)
        val result = (1..100).fold(floor) {acc, it -> day(acc)}
        return result.filter { it.value == BLACK }.size
    }

    fun day(fields: Map<Field, HexColor>): Map<Field, HexColor> {
        val blacks = fields.filter { it.value == BLACK }
        val blacksWithNeighbours: List<Pair<Map.Entry<Field, HexColor>, List<Pair<Field, HexColor>>>> =
                blacks.map { black -> black to black.key.neighbours().map { it to (fields.get(it) ?: WHITE) } }
        val blacksToWhite = blacksWithNeighbours.filter { black -> shouldMutateBlack(black.second) }
                .map { it.first }.map { it.key}
        val whiteToBlacks = blacksWithNeighbours.toList().map { black -> black.second.filter { it.second == WHITE } }.flatten()
                .groupBy { it }.filter { shouldMutateWhite(it) }.keys.map { it.first }
        return fields + whiteToBlacks.map { it to BLACK }.toMap() + blacksToWhite.map { it to WHITE }.toMap()
    }

    fun shouldMutateBlack(neighbours: List<Pair<Field, HexColor>>): Boolean {
        val size = neighbours.filter { it.second == BLACK }.size

        return size == 0 || size > 2
    }

    fun shouldMutateWhite(entry: Map.Entry<Pair<Field, HexColor>, List<Pair<Field, HexColor>>>): Boolean {
        val size = entry.value.size
        return size == 2
    }

    fun run(fields: Map<Field, HexColor>, directions: List<Direction>): Map<Field, HexColor> {
        val f = run(directions)
        if (fields.get(f) ?: WHITE == WHITE) {
            return fields + (f to BLACK)
        } else {
            return fields + (f to WHITE)
        }
    }

    fun run(directions: List<Direction>): Field {
        return directions.fold(Field(0, 0, 0)) { acc, dir ->
            acc + dir
        }
    }

    fun parseInput(input: List<String>): List<List<Direction>> {
        return input.map { row ->
            row.fold(emptyList<Direction>() to null, this::parseChar).first
        }
    }

    fun parseChar(acc: Pair<List<Direction>, Char?>, char: Char): Pair<List<Direction>, Char?> {
        val (list, lastChar) = acc
        if (lastChar == 's') {
            return when (char) {
                'e' -> (list + listOf(SE)) to null
                'w' -> (list + listOf(SW)) to null
                else -> error("Bad char $char")
            }
        }
        if (lastChar == 'n') {
            return when (char) {
                'e' -> (list + listOf(NE)) to null
                'w' -> (list + listOf(NW)) to null
                else -> error("Bad char $char")
            }
        }
        assert(lastChar == null) { "Bad char $char" }
        return when (char) {
            'e' -> (list + listOf(E)) to null
            'w' -> (list + listOf(W)) to null
            's', 'n' -> list to char
            else -> error("Bad char $char")
        }
    }
}


enum class HexColor { WHITE, BLACK }
enum class Direction(val x: Int, val y: Int, val z: Int) {
    E(1, -1, 0),
    SE(0, -1, 1),
    SW(-1, 0, 1),
    W(-1, 1, 0),
    NW(0, 1, -1),
    NE(1, 0, -1)
}

data class Field(val x: Int, val y: Int, val z: Int) {
    operator fun plus(direction: Direction): Field {
        return Field(x + direction.x, y + direction.y, z + direction.z)
    }

    fun neighbours(): List<Field> {
        return Direction.values().map { this + it }
    }
}

