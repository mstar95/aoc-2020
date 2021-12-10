package days


class Day7 : Day(7) {

    override fun partOne(): Any {
        val bags: Map<String, List<String>> = parse(inputList)
                .map { it.key to it.value.map { p -> p.first } }
                .toMap()
        val bag = bags.keys.map { findBag(bags, it, "shiny gold bag") }
        return bag.filter { it }.size
    }

    override fun partTwo(): Any {
        val bags = parse(inputList)
        println(bags)
        val count = countBadges(bags, "shiny gold bag") - 1
        return count
    }
}

private fun parse(inputList: List<String>): Map<String, List<Pair<String, Int>>> {
    return inputList.map { parse(it) }.toMap()
}

fun parse(inputList: String): Pair<String, List<Pair<String, Int>>> {
    val spliced = inputList.split(" contain ")
    require(spliced.size == 2) { "Splited should have size of 2" }
    val key = bagsToBag(spliced[0])
    val rest = spliced[1].split(", ")
    val bags = parseBags(rest)
    return key to bags
}

private fun parseBags(bags: List<String>): List<Pair<String, Int>> {
    if (bags.size == 1) {
        if (bags[0] == "no other bags.") {
            return emptyList()
        }
    }
    return bags.map { deleteDot(it) }.map { bagsToBag(it) }
            .map { deleteNumbers(it) to it[0].toString().toInt() }
}

private fun deleteNumbers(it: String) = it.drop(2)

private fun deleteDot(it: String) = if (it.contains('.')) it.dropLast(1) else it

private fun bagsToBag(s: String) = s.replace("bags", "bag")

val memo: MutableMap<Pair<String,String>, Boolean> = mutableMapOf()

fun findBag(map: Map<String, List<String>>, actual: String, bag: String): Boolean {
    val memoized = memo[actual to bag]
    if(memoized != null) {
        return memoized
    }
    val bags = map[actual] ?: error("No key $actual")
    val result = if (bags.contains(bag)) true
    else bags.asSequence()
            .map { findBag(map, it, bag) }
            .any { it }
    memo[actual to bag] = result
    return result
}
val memo2: MutableMap<String, Int> = mutableMapOf()

fun countBadges(map: Map<String, List<Pair<String, Int>>>, actual: String): Int {
    val memoized = memo2[actual]
    if(memoized != null) {
        return memoized
    }
    val bags = map[actual] ?: error("No key $actual")
    if (bags.isEmpty()) {
        return 1
    }
    val sum = bags.map { it.second * countBadges(map, it.first) }.sum() + 1
    memo2[actual] = sum
    return sum
}

