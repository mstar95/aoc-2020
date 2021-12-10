package days


class Day13 : Day(13) {

    override fun partOne(): Any {
        val timestamp = inputList.first().toInt()
        val input = prepareInput(inputList).map { it.second }
        val result = findBus(timestamp, input)
        val minutes = result.second - timestamp
        println("$result, $minutes, $timestamp")
        return minutes * result.first
    }

    override fun partTwo(): Any {
        val input = prepareInput(inputList)
        val res = compute(0, 1, input)
        println(res)
        return res
    }

    private fun compute(number: Long, rest: Long, buses: List<Pair<Int, Int>>): Long {
        if (buses.isEmpty()) {
            return number
        }
        val bus = buses.first()
        val nextNumber = next(number,rest, bus.first, bus.second)
        println(" next: " + nextNumber)
        return compute(nextNumber, bus.second * rest, buses.drop(1))
    }

    private fun next(number: Long, rest: Long, offset: Int, bus: Int): Long {
        //  println(number)
        if ((number + offset) % bus == 0L) {
            return number;
        }
        return next(number + rest, rest, offset, bus)
    }

    private fun prepareInput(list: List<String>): List<Pair<Int, Int>> {
        val split = list[1]
                .split(",")
        return split
                .mapIndexed { id, it -> id to it }
                .filter { it.second != "x" }
                .map { it.first to it.second.toInt() }
    }

    private fun findBus(timestamp: Int, buses: List<Int>): Pair<Int, Int> {
        return buses.map {
            it to departTime(timestamp, it)
        }.minByOrNull { it.second }!!
    }

    private fun departTime(timestamp: Int, bus: Int): Int {
        val times = timestamp / bus
        val depart = bus * times
        if (depart < timestamp) {
            return depart + bus
        }
        return depart
    }

}

