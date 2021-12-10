package days


class Day14 : Day(14) {

    override fun partOne(): Any {
        val input = prepareInput(inputList)
        val result = input.fold(Program(Mask(""), mutableMapOf())) { program, op -> program.op(op, program::write) }

        println(input)
        print(result)
        return result.memory.values.sum()
    }

    override fun partTwo(): Any {
        val input = prepareInput(inputList)
        val result = input.fold(Program(Mask(""), mutableMapOf())) { program, op -> program.op(op, program::write2) }

        println(input)
        print(result)
        return result.memory.values.sum()
    }

    private fun prepareInput(list: List<String>): List<Operation> {
        return list
                .map { if (it.startsWith("mask")) createMask(it) else createWrite(it) }

    }

    private fun createMask(string: String) = Mask(string.drop("mask = ".length))

    private fun createWrite(string: String): Write {
        val split = string.drop("mem[".length).split("]")

        val address = split[0].toLong()
        val int = split[1].drop(" = ".length).toLong()
        return Write(address, int)
    }
}

private sealed class Operation
private data class Mask(val mask: String) : Operation()
private data class Write(val address: Long, val int: Long) : Operation()
private data class Program(var mask: Mask, val memory: MutableMap<Long, Long>) {
    fun op(op: Operation, w: (Write) -> Unit): Program {
        if(op is Mask) {
            mask = op
        }
        if(op is Write) {
            w(op)
        }
        return this
    }

    fun write(write: Write) {
        val curr = write.int.toString(2).padStart(36, '0')
        assert(curr.length == mask.mask.length ) {"$curr ${curr.length} $mask ${mask.mask.length}"}
        val toSave = mask.mask.foldIndexed(curr) { idx, value, m ->
            when (m) {
                'X' -> value
                '1' -> put(value, idx, '1')
                '0' -> put(value, idx, '0')
                else -> error("Unsupported value $value")
            }
        }
        println(toSave)
        memory[write.address] = toSave.toLong(2)
    }

    private fun put(s: String, idx: Int, value: Char) = s.substring(0, idx) + value + s.substring(idx + 1)

    fun write2(write: Write) {
        val curr = write.address.toString(2).padStart(36, '0')
        assert(curr.length == mask.mask.length ) {"$curr ${curr.length} $mask ${mask.mask.length}"}
        val addresses = mask.mask.foldIndexed(listOf(curr)) { idx, list, m ->
            when (m) {
                'X' -> list.flatMap { listOf(put(it, idx, '0'), put(it, idx, '1')) }
                '1' -> list.map { put(it, idx, '1') }
                '0' -> list
                else -> error("Unsupported value $m")
            }
        }
        addresses.forEach{
            memory[it.toLong(2)] = write.int
        }
    }

}
