package days

class Day25 : Day(25) {

    val subjectNumber = 7
    val modulo = 20201227

    override fun partOne(): Any {
        val cardKey = 8252394L
        val doorKey = 6269621L
        val cardLoopSize = findLoopSize(cardKey)
        println(cardLoopSize)
        val doorLoopSize = findLoopSize(doorKey)
        println(doorLoopSize)
        println(performLoop(cardKey, doorLoopSize))
        println(performLoop(doorKey , cardLoopSize))
        return 1
    }

    override fun partTwo(): Any {
        return 1
    }

    fun findLoopSize(key: Long): Int {
        var value = 1L
        var loopSize = 0
        while (value != key) {
            value = (value * subjectNumber) % modulo
            loopSize++
        }
        return loopSize
    }

    fun performLoop(publicKey: Long, loopSize: Int): Long {
        var value = 1L
        for (i in 1..loopSize) {
            value = (value * publicKey) % modulo
        }
        return value
    }
}