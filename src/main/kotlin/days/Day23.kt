package days

import java.util.*

data class LLNode(val value: Int, var next: LLNode?)
class Day23 : Day(23) {

    override fun partOne(): Any {
        val cups = inputString.split("").mapNotNull { if (it == "") null else LLNode(it.toInt(), null) }
        for (i in cups.indices) {
            cups[i].next = cups[(i+1)%cups.size]
        }

        var curr: LLNode? = cups[0]
        for (i in 1..100) {
            val move = curr?.next
            curr?.next = curr?.next?.next?.next?.next

            var destination = if (curr?.value == 1) 9 else curr!!.value - 1
            while (destination in listOf(move?.value, move?.next?.value, move?.next?.next?.value)) {
                destination -= 1
                if (destination < 1) {
                    destination = 9
                }
            }

            var dest = curr.next
            while (dest?.value != destination) {
                dest = dest?.next
            }

            move?.next?.next?.next = dest.next
            dest.next = move
            curr = curr.next
        }

        var one = curr
        while (one?.value != 1) {
            one = one?.next
        }
        while (one?.next?.value != 1) {
            print(one?.next?.value)
            one = one?.next
        }
        println()
        return 0
    }

    override fun partTwo(): Any {
        val cups = inputString.split("").mapNotNull { if (it == "") null else LLNode(it.toInt(), null) }
        for (i in cups.indices) {
            cups[i].next = cups[(i+1)%cups.size]
        }
        var curr: LLNode = cups[8]
        for (i in 10..1000000) {
            curr.next = LLNode(i, null)
            curr = curr.next!!
        }
        curr.next = cups[0]
        curr = curr.next!!
        var cup_map = mutableMapOf<Int, LLNode>(curr.value to curr)
        var cc = curr.next!!
        while (cc != curr) {
            cup_map[cc.value] = cc
            cc = cc.next!!
        }

        for (i in 1..10000000) {
            val move = curr.next
            curr.next = curr.next?.next?.next?.next

            var destination = if (curr.value == 1) 1000000 else curr.value - 1
            while (destination in listOf(move?.value, move?.next?.value, move?.next?.next?.value)) {
                destination -= 1
                if (destination < 1) {
                    destination = 1000000
                }
            }

            var dest = cup_map[destination]

            move?.next?.next?.next = dest!!.next
            dest.next = move
            curr = curr.next!!
        }

        var one = cup_map[1]!!
        println("${one.next!!.value.toLong() * one.next!!.next!!.value.toLong()}")
        return 0
    }


}




