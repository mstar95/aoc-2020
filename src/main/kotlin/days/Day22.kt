package days


class Day22 : Day(22) {
    override fun partOne(): Any {
        val (deck1, deck2) = prepareInput(inputString)
        val winner = play(deck1.toMutableList(), deck2.toMutableList())
        return winner.foldIndexed(0) { idx, acc, elem -> acc + elem * (winner.size - idx) }
    }

    override fun partTwo(): Any {
        val (deck1, deck2) = prepareInput(inputString)
        val winner = recursivePlay(deck1.toMutableList(), deck2.toMutableList())
        return winner.second.foldIndexed(0) { idx, acc, elem -> acc + elem * (winner.second.size - idx) }
    }

    private fun play(deck1: MutableList<Int>, deck2: MutableList<Int>): List<Int> {
        var c1: Int
        var c2: Int
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
            c1 = deck1.removeFirst()
            c2 = deck2.removeFirst()
            if (c1 > c2) {
                deck1.add(c1)
                deck1.add(c2)
            }
            if (c1 < c2) {
                deck2.add(c2)
                deck2.add(c1)
            }
            if (c1 == c2) {
                error("same cards")
            }
        }
        return if (deck1.isNotEmpty()) deck1 else deck2
    }

    private fun recursivePlay(deck1: MutableList<Int>, deck2: MutableList<Int>): Pair<Int, List<Int>> {
        var c1: Int
        var c2: Int
        val history = mutableSetOf<Pair<MutableList<Int>, MutableList<Int>>>()
        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
            if (history.contains(deck1 to deck2)) {
                return 1 to deck1
            }
            history.add(deck1 to deck2)

            c1 = deck1.removeFirst()
            c2 = deck2.removeFirst()
            if (c1 <= deck1.size && c2 <= deck2.size) {
                val res = recursivePlay(deck1.toMutableList().subList(0, c1), deck2.toMutableList().subList(0, c2))
                if (res.first == 1) {
                    deck1.add(c1)
                    deck1.add(c2)
                } else {
                    deck2.add(c2)
                    deck2.add(c1)
                }
                continue
            }

            if (c1 > c2) {
                deck1.add(c1)
                deck1.add(c2)
            }
            if (c1 < c2) {
                deck2.add(c2)
                deck2.add(c1)
            }
            if (c1 == c2) {
                error("same cards")
            }
        }
        return if (deck1.isNotEmpty()) 1 to deck1 else 2 to deck2
    }

    fun prepareInput(input: String) =
            input.split("\n\n")
                    .map { d -> d.split("\n").drop(1).map { it.toInt() } }
                    .let { it[0] to it[1] }

}

