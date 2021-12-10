package days

import intcode.IntCodeProgram


class Day101 : Day(101) {

    override fun partOne(): Any {
        val programs = inputList
        val outputs = programs.map { compute(it) }
        return outputs.map { it.toString() }
    }

    fun compute(program: String): Int {
        val programInput = program.split(",")

        val s = listOf(0,1,2,3,4).permutations().map {
            result(programInput, it)[0]
        }
        return s.maxOrNull() ?: 0
    }

    private fun result(programInput: List<String>, input: List<Int>): List<Int> =
            input.fold(emptyList(), { acc, elem ->
                IntCodeProgram(programInput, listOf(elem) + acc).calculate()
            })

    override fun partTwo(): Any {
        val a: List<Unit?>  = emptyList()
        return 0
    }

    fun a(): Unit? {
        return null
    }
}

fun <T> List<T>.permutations(): List<List<T>> =
        if (this.size <= 1) listOf(this)
        else {
            val elementToInsert = first()
            drop(1).permutations().flatMap { permutation ->
                (0..permutation.size).map { i ->
                    permutation.toMutableList().apply { add(i, elementToInsert) }
                }
            }
        }

