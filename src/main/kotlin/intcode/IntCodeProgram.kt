package intcode

import arrow.core.Either

data class IntCodeProgram(val program: List<String>, val input: List<Int>, val output: List<Int> = listOf(), val cursor: Int = 0, val inputCursor: Int = 0) {

    private val current = program[cursor].padStart(2, '0')
    private fun modes(n: Int) = current
            .padStart(n + 2, '0')
            .take(n)
            .reversed()
            .map { Mode.from(it.toString()) }

    private fun threeArgsOpCode(constructor: (Int, Int, Int) -> Instruction): Instruction {
        return constructor(parameter(1, 3), parameter(2, 3), immediate(3))
    }


    private fun twoArgsOpCode(constructor: (Int, Int) -> Instruction): Instruction {
        return constructor(parameter(1, 3), parameter(2, 3))
    }

    private fun parameter(next: Int, of: Int): Int = when (modes(of)[next - 1]) {
        Mode.IMMEDIATE -> immediate(next)
        Mode.POSITION -> program[program[cursor + next].toInt()].toInt()
    }

    private fun immediate(next: Int) = program[cursor + next].toInt()

    fun calculate(): List<Int> {
        //println("${program[cursor]} ${modes(3)}  ${program.getOrNull(cursor + 1)} ${program.getOrNull(cursor + 2)} ${program.getOrNull(cursor + 3)}")
        if (current.takeLast(2) == "99") {
            return output
        }
        val instruction = when (current.takeLast(2)) {
            Addition.opCode -> threeArgsOpCode(::Addition)
            Multiplication.opCode -> threeArgsOpCode(::Multiplication)
            Input.opCode -> Input(input.getOrElse(inputCursor) { 0 }, immediate(1))
            Output.opCode -> Output(parameter(1, 1))
            TrueIf.opCode -> twoArgsOpCode(::TrueIf)
            FalseIf.opCode -> twoArgsOpCode(::FalseIf)
            LessThan.opCode -> threeArgsOpCode(::LessThan)
            Equal.opCode -> threeArgsOpCode(::Equal)
            else -> error("Uknowm opcode ${current.takeLast(2)}")
        }
        val result = instruction.calc()
       // println("$result, $instruction")
        val result2 = result?.fold({ program }) { program.updated(instruction.save(), it.toString()) } ?: program
        val output2 = result?.swap()?.fold({ output }) { output + it } ?: output
        val inputCursor2 = inputCursor + instruction.inputPointer()
        return IntCodeProgram(result2, input, output2, instruction.pointer(cursor), inputCursor2).calculate()
    }

}

enum class Mode(val value: String) {
    IMMEDIATE("1"), POSITION("0");

    companion object {
        fun from(value: String): Mode = values().find { it.value == value }!!
    }
}

data class Addition(val a: Int, val b: Int, val save: Int) : Instruction {
    override fun pointer(cursor: Int): Int = 4 + cursor

    override fun calc() = Either.right(a + b)

    override fun save(): Int = save

    companion object {
        const val opCode = "01"
    }
}

data class Multiplication(val a: Int, val b: Int, val save: Int) : Instruction {
    override fun pointer(cursor: Int): Int = cursor + 4

    override fun calc() = Either.right(a * b)

    override fun save(): Int = save

    companion object {
        const val opCode = "02"
    }
}

data class Input(val input: Int, val save: Int) : Instruction {

    override fun pointer(cursor: Int): Int = cursor + 2

    override fun calc() = Either.right(input)

    override fun save(): Int = save

    override fun inputPointer(): Int {
        return 1
    }

    companion object {
        const val opCode = "03"
    }
}

data class Output(val input: Int) : Instruction {

    override fun pointer(cursor: Int): Int = cursor + 2

    override fun calc() = Either.left(input)

    override fun save(): Int = -1

    companion object {
        const val opCode = "04"
    }
}

data class TrueIf(val query: Int, val onTrue: Int) : Instruction {

    override fun pointer(cursor: Int): Int = if (query != 0) onTrue else cursor + 3

    override fun calc(): Either<Int, Int>? = null

    override fun save(): Int = -1

    companion object {
        const val opCode = "05"
    }
}

data class FalseIf(val query: Int, val onTrue: Int) : Instruction {

    override fun pointer(cursor: Int): Int = if (query == 0) onTrue else cursor + 3

    override fun calc(): Either<Int, Int>? = null

    override fun save(): Int = -1

    companion object {
        const val opCode = "06"
    }
}

data class LessThan(val a: Int, val b: Int, val save: Int) : Instruction {
    override fun pointer(cursor: Int): Int = cursor + 4

    override fun calc() = Either.right(if (a < b) 1 else 0)

    override
    fun save(): Int = save

    companion object {
        const val opCode = "07"
    }
}

data class Equal(val a: Int, val b: Int, val save: Int) : Instruction {
    override fun pointer(cursor: Int): Int = cursor + 4

    override fun calc() = Either.right(if (a == b) 1 else 0)

    override
    fun save(): Int = save

    companion object {
        const val opCode = "08"
    }
}


interface Instruction {
    fun pointer(cursor: Int): Int
    fun calc(): Either<Int, Int>?
    fun save(): Int
    fun inputPointer(): Int = 0
}

fun List<String>.updated(index: Int, elem: String) = mapIndexed { i, existing -> if (i == index) elem else existing }
fun List<Int>.updated(index: Int, elem: Int) = mapIndexed { i, existing -> if (i == index) elem else existing }

