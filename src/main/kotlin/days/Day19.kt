package days

import arrow.core.extensions.list.foldable.isNotEmpty


class Day19 : Day(19) {

    override fun partOne(): Any {
        val (grammars, words) = prepareInput(inputList)
        val map = words.map { parse(grammars, it, grammars[0]!!) }
      //  println(map)
        return map.filter { it.isNotEmpty() && it[0].isEmpty() }.size
    }

    //451 za duza
    //270 za malo
    private fun parse(grammars: Map<Int, Grammar>, word: String, grammar: Grammar): List<String> {
     //   println("${word.length} $grammar $word ")
        if (word.isEmpty()) {
            return emptyList()
        }
        return when (grammar) {
            is Literal -> {
                val literal = word.first()
                return if (literal == grammar.value) {
                    listOf(word.drop(1))
                } else {
                    emptyList()
                }
            }
            is Rule -> grammar.left.fold(listOf(word)) { w: List<String>, id: Int ->
                w.flatMap { parse(grammars, it, grammars[id]!!) }
            } + (grammar.right?.fold(listOf(word)) { w: List<String>, id: Int ->
                w.flatMap { parse(grammars, it, grammars[id]!!) }
            } ?: emptyList())
        }
    }


    override fun partTwo(): Any {
        return 0
    }


    private fun prepareInput(input: List<String>): Pair<Map<Int, Grammar>, List<String>> {
        val grammars = mutableMapOf<Int, Grammar>()
        val messages = mutableListOf<String>()
        input.forEach { row ->
            if (row.contains(":")) {
                val splitted: List<String> = row.split(": ")
                val key = splitted[0].toInt()
                val rest = splitted[1]
                grammars[key] = prepareGrammar(rest, key)
            } else {
                if (row.isNotEmpty()) {
                    messages.add(row)
                }
            }
        }
        grammars[8] = Rule(8, listOf(42), listOf(42, 8))
        grammars[11] = Rule(11, listOf(42, 31), listOf(42, 11, 31))
        return grammars.toMap() to messages.toList()
    }

    private fun prepareGrammar(rest: String, key: Int) = if (rest.contains("\"")) {
        Literal(key, rest[1])
    } else {
        val rules: List<List<Int>> = rest.split(" | ").map { it.split(" ").map { it.toInt() } }
        Rule(key, rules[0], rules.getOrNull(1))
    }
}

private sealed class Grammar
private data class Literal(val id: Int, val value: Char) : Grammar()
private data class Rule(val id: Int, val left: List<Int>, val right: List<Int>?) : Grammar()

//Rule(id=31, left=[14, 17], right=[1, 13]) bbbbbaabaaabaaa
//Rule(id=11, left=[42, 31], right=[42, 11, 31]) bbbbbaabaaabaaa
//Rule(id=8, left=[42], right=[42, 8]) aaaaabbaabaaaaababaa

// 11: bbaabaaaaababaa
//42- bbaab
//41 - aaaaababaa
//aaaaababaa
//babaa