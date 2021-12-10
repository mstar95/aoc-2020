package `2021`

import days.Day

class Day10 : Day(10) {

    override fun partOne(): Any {
        return inputList.map { it.map { it.toString() } }.mapNotNull {
            calculate(it).left
        }.sum()
    }

    private fun calculate(it: List<String>): Either {
        val deque = ArrayDeque<String>()
        it.forEach {
            when (it) {
                ")" -> {
                    if (deque.removeFirst() != "(") {
                        return Either(left = 3)
                    }
                }
                "]" -> {
                    if (deque.removeFirst() != "[") {
                        return Either(left = 57)
                    }
                }
                "}" -> {
                    if (deque.removeFirst() != "{") {
                        return Either(left = 1197)
                    }
                }
                ">" -> {
                    if (deque.removeFirst() != "<") {
                        return Either(left = 25137)
                    }
                }
                else -> deque.addFirst(it)
            }
        }
        return Either(right = deque.fold(0L) { acc, elem ->
            val acc2 = (5 * acc)
            when (elem) {
                "(" -> acc2 + 1
                "[" -> acc2 + 2
                "{" -> acc2 + 3
                "<" -> acc2 + 4
                else -> throw IllegalStateException("$it")
            }
        })
    }


    data class Either(val left: Int? = null, val right: Long? = null)


    override fun partTwo(): Any {
        val sorted = inputList.map { it.map { it.toString() } }.mapNotNull {
            calculate(it).right
        }.sorted()
        return sorted.get(sorted.size / 2)
    }
}
//212829295
