package days

import days.Action.E
import days.Action.F
import days.Action.L
import days.Action.N
import days.Action.R
import days.Action.S
import days.Action.W
import days.Action.valueOf
import kotlin.math.absoluteValue


class Day12 : Day(12) {

    override fun partOne(): Any {
        val input = prepareInput(inputList)
        //printRoad(input)
        val result = input.fold(Ship()) { acc, it -> acc.action(it) }
        println(result)
        return result.point.x.absoluteValue + result.point.y.absoluteValue
    }

    override fun partTwo(): Any {
        val input = prepareInput(inputList)
        //printRoad(input)
        val result = input.fold(Ship()) { acc, it -> acc.actionWaypoint(it) }
        println(result)
        return result.point.x.absoluteValue + result.point.y.absoluteValue
    }

    private fun prepareInput(list: List<String>): List<Pair<Action, Int>> = list
            .map { valueOf(it.first().toString()) to it.drop(1).toInt() }

}

private data class Ship(val direction: Action = E, val point: Point = Point(0, 0), val waypoint: Waypoint = Waypoint()) {
    fun action(action: Pair<Action, Int>): Ship = when (action.first) {
        N, S, E, W -> move(action.first, action.second)
        L, R -> turn(action.first, action.second)
        F -> move(this.direction, action.second)
    }

    fun actionWaypoint(action: Pair<Action, Int>): Ship {
        val ship = when (action.first) {
            N, S, E, W -> copy(waypoint = waypoint.move(action.first, action.second))
            L, R -> copy(waypoint = waypoint.turn(action.first, action.second))
            F -> moveToWaypoint(action.second)
        }
        print(action)
        println(ship)
        return ship
    }

    private fun moveToWaypoint(value: Int): Ship {
        return copy(point = Point(point.x + waypoint.x * value, point.y + waypoint.y * value))
    }

    private fun turn(action: Action, value: Int): Ship {
        if (value % 90 != 0) {
            throw error("Unsupported degree value $value")
        }
        val times = value / 90

        return when (action) {
            L -> turnLeft(direction, times)
            R -> turnRight(direction, times)
            else -> throw error("Unsupported action $action")
        }
    }

    private fun turnLeft(current: Action, times: Int): Ship {
        if (times == 0) {
            return Ship(current, point)
        }

        return when (current) {
            N -> turnLeft(W, times - 1)
            S -> turnLeft(E, times - 1)
            E -> turnLeft(N, times - 1)
            W -> turnLeft(S, times - 1)
            else -> throw error("Unsupported direction $current")
        }
    }

    private fun turnRight(current: Action, times: Int): Ship {
        if (times == 0) {
            return Ship(current, point)
        }

        return when (current) {
            N -> turnRight(E, times - 1)
            S -> turnRight(W, times - 1)
            E -> turnRight(S, times - 1)
            W -> turnRight(N, times - 1)
            else -> throw error("Unsupported direction $current")
        }
    }

    private fun move(action: Action, value: Int): Ship = when (action) {
        N -> Ship(direction, point + Point(0, value))
        S -> Ship(direction, point + Point(0, -value))
        E -> Ship(direction, point + Point(-value, 0))
        W -> Ship(direction, point + Point(value, 0))
        else -> throw error("Unsupported operation $action")
    }
}

data class Waypoint(val point: Point = Point(10, 1)) {
    val x: Int
        get() = point.x
    val y: Int
        get() = point.y

    fun move(action: Action, value: Int): Waypoint = when (action) {
        N -> Waypoint(point + Point(0, value))
        S -> Waypoint(point + Point(0, -value))
        E -> Waypoint(point + Point(value, 0))
        W -> Waypoint(point + Point(-value, 0))
        else -> throw error("Unsupported operation $action")
    }

    fun turn(action: Action, value: Int): Waypoint {
        if (value % 90 != 0) {
            throw error("Unsupported degree value $value")
        }
        val times = value / 90

        return when (action) {
            L -> turnLeft(times)
            R -> turnRight(times)
            else -> throw error("Unsupported action $action")
        }
    }

    private fun turnLeft(times: Int): Waypoint {
        if (times == 0) {
            return this
        }
        return Waypoint(Point(y*-1, x)).turnLeft(times - 1)
    }

    private fun turnRight(times: Int): Waypoint {
        if (times == 0) {
            return this
        }
        return Waypoint(Point(y, x * -1)).turnRight(times - 1)
    }
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(another: Point): Point {
        return Point(x + another.x, y + another.y)
    }
}

enum class Action { N, S, E, W, L, R, F }

//
//           .1,2
//-2, 1
//
//       0,0.
//                   2, -1
//
//  -1,-2

//55145