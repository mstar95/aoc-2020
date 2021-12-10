package days

import arrow.core.Either
import arrow.core.flatMap


class Day8 : Day(8) {

    override fun partOne(): Any {
//        val instructions = inputList.map { it.split(" ") }.map { it[0] to it[1].toInt() }
//        println(instructions)
//        return Program(instructions).run()
        return 0;
    }

    override fun partTwo(): Any {
        val instructions = inputList.map { it.split(" ") }.map { it[0] to it[1].toInt() }
        return Program8(instructions).run().fold({ 0 }, { it })
    }
}

private data class Program8(val instructions: List<Pair<String, Int>>) {
    fun run(step: Int = 0, accumulator: Int = 0, performed: Set<Int> = setOf(), modifiedStep: Int? = null): Either<Int?, Int> {
        if (step == instructions.size) {
            return Either.right(accumulator)
        }
        if (performed.contains(step)) {
            return Either.left(modifiedStep)
        }
        val instruction = instructions[step]
        val either = when (instruction.first) {
            "nop" -> nop(step, accumulator, performed, modifiedStep)
            "acc" -> acc(step, instruction, accumulator, performed, modifiedStep)
            "jmp" -> jmp(step, instruction, accumulator, performed, modifiedStep)
            else -> error("Not known instruction $instruction, $step")
        }
        if (either.isRight()) {
            return either
        }
        return either.swap().flatMap {
            if(performed.contains(it)) Either.left(it) else
            when (instruction.first) {
                "nop" -> jmp(step, instruction, accumulator, performed, step)
                "acc" -> Either.left(it)
                "jmp" -> nop(step, accumulator, performed, step)
                else -> error("Not known instruction $instruction, $step")
            }

        }
    }

    private fun jmp(step: Int, instruction: Pair<String, Int>, accumulator: Int, performed: Set<Int>, modifiedStep: Int?): Either<Int?, Int> =
            run(step + instruction.second, accumulator, performed + step, modifiedStep)

    private fun acc(step: Int, instruction: Pair<String, Int>, accumulator: Int, performed: Set<Int>, modifiedStep: Int?) =
            run(step + 1, accumulator + instruction.second, performed + step, modifiedStep)

    private fun nop(step: Int, accumulator: Int, performed: Set<Int>, modifiedStep: Int?) =
            run(step + 1, accumulator, performed + step, modifiedStep)

}

