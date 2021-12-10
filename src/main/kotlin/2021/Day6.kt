package `2021`

import days.Day

class Day6 : Day(6) {

    override fun partOne(): Any {
        val input = inputString.split(",").map { it.toInt() }
        val grouped = input.groupBy { it }.mapValues { it.value.size.toLong() }
        var fishes = Fishes(grouped.toMutableMap(), mutableMapOf())
        repeat(256) {
            fishes = runFast(fishes, it)
        //    println("After $it $fishes")
        }
        //372300
        return fishes.size()
    }

    override fun partTwo(): Any {
        val input = inputString.split(",").map { it.toInt() }
        val grouped = input.groupBy { it }.mapValues { it.value.size.toLong() }
        var fishes = Fishes(grouped.toMutableMap(), mutableMapOf())
        repeat(256) {
            fishes = runFast(fishes, it)
            //    println("After $it $fishes")
        }
        //372300
        return fishes.size()
    }


    fun run(fishes: List<Int>): List<Int> {
        var kids = 0
        val newFishes = fishes.map {
            if (it == 0) {
                kids++
                6
            } else it - 1
        }
        return newFishes + (0 until kids).map { 8 }.toList()
    }

    fun runFast(fishes: Fishes, day: Int): Fishes {
        val dayOfWeek = day % 7
        fishes.children[dayOfWeek]?.let {
            fishes.children.remove(day, dayOfWeek)
            val parents = fishes.parents[(dayOfWeek + 2) % 7] ?: 0
            fishes.parents[(dayOfWeek + 2) % 7] = parents + it
        }

        fishes.getNewChildren(day)?.let { fishes.addChildren(day, it) }
        return fishes
    }

    data class Fishes(val parents: MutableMap<Int, Long>, val children: MutableMap<Int, Long>) {
        fun addChildren(day: Int, number: Long) {
            val count = children.getOrDefault(day, 0)
            children.put(day % 7, count + number)
        }

        fun getNewChildren(day: Int): Long? = parents.get(day % 7)

        fun size() = parents.map { it.value }.sum() + children.map { it.value }.sum()

    }


}

