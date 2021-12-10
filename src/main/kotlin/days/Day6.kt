package days

import util.groupByEmpty

class Day6 : Day(6) {

    override fun partOne(): Any {
        val groups: List<List<String>> = groupByEmpty(inputList)
        val forms = flatten(groups)
        val answers = forms.map { form -> form.distinct().size }
        return answers.fold(0, {sum, elem -> sum + elem});
    }

    override fun partTwo(): Any {
        val groups: List<List<String>> = groupByEmpty(inputList)
        val answers = groups.map { countCommon(it) }
        print(answers)
        return answers.fold(0, {sum, elem -> sum + elem});
    }

}

private fun countCommon(group: List<String>): Int {
    return group.map { it.toSet() }
            .reduce { acc, set -> acc.intersect(set) }
            .size
}

private fun flatten(groups: List<List<String>>): List<List<String>> {
    return groups.map { group ->
        group.flatMap { letters ->
            letters.map { it.toString() }
        }
    }
}

