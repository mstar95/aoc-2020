package days

import util.groupByEmpty


class Day16 : Day(16) {

    override fun partOne(): Any {
        val input = prepareInput(inputList)
        //println(input)
        val (validators, myTicket, otherTickets) = input
        val bad = otherTickets.flatMap { getInvalidFields(it, validators) }
        //println(bad)
        return bad.sum()
    }

    override fun partTwo(): Any {
        val input = prepareInput(inputList)
        val (validators, myTicket, otherTickets) = input
        val good = otherTickets.filter { validateTickets(it, validators) }
        val columns = collectColumns(good + listOf(myTicket))
        val filter = findValidators(columns, validators)
                .filter { it.value.startsWith("departure") }
        val ticket = filter
                .map { myTicket[it.key] }
        val found = ticket
                .fold(1L){x,y -> x*y}
        println(filter)
        println(ticket)
        println(found)
        return found
    }

    fun findValidators(columns: List<List<Int>>, validators: List<Validator>): Map<Int, String> {
        val seen = mutableListOf<Validator>()
        return columns.mapIndexed { idx, column ->
            idx to validators.filter { validator -> column.all { validator.validate(it) } }
        }.sortedBy { it.second.size }
                .map { pair -> pair.first to pair.second.first { !seen.contains(it) }.also { seen.add(it) }}
                .map { it.first to it.second.field }.toMap()

    }

    fun getInvalidFields(ticket: List<Int>, validators: List<Validator>) = ticket.filter { number ->
        validators.all { !it.validate(number) }
    }

    fun validateTickets(ticket: List<Int>, validators: List<Validator>) = ticket.all { number ->
        validators.any { it.validate(number) }
    }


    private fun prepareInput(input: List<String>): Triple<List<Validator>, List<Int>, List<List<Int>>> {
        val groups: List<List<String>> = groupByEmpty(input)
        val validators: List<Validator> = groups[0].map { it.split(": ") }.map {
            val field = it[0]
            val ranges = it[1].split(" or ").map { f ->
                val splitted = f.split('-')
                splitted[0].toInt() to splitted[1].toInt()
            }
            Validator(field, ranges)
        }
        assert(groups[1][0] == "your ticket:")
        val myTicket = groups[1][1].split(",").map { it.toInt() }
        assert(groups[2][0] == "nearby tickets:")
        val otherTickets = groups[2].drop(1).map { row -> row.split(",").map { it.toString().toInt() } }
        return Triple(validators, myTicket, otherTickets)
    }

    private fun collectColumns(tickets: List<List<Int>>): List<List<Int>> {
        val rows: MutableList<MutableList<Int>> = mutableListOf()
        tickets[0].forEach { rows.add(mutableListOf()) }
        tickets.forEach { ticket -> ticket.forEachIndexed { idx, it -> rows[idx].add(it) } }
        return rows
    }
}


data class Validator(val field: String, val ranges: List<Pair<Int, Int>>) {
    fun validate(value: Int): Boolean {
        return ranges.any { it.first <= value && it.second >= value }
    }
}
