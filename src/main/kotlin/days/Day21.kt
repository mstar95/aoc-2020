package days

inline class Allergen(val value: String)
inline class Ingredient(val value: String)
class Day21 : Day(21) {
    override fun partOne(): Any {
        val entries = prepareInput(inputList)
        val allergens = basicAllergens(entries)
        val safe = entries.safeIngredients(allergens)
        // println(safe)
        return safe.map { s -> entries.entries.count { it.ingredients.contains(s) } }.sum()
    }

    override fun partTwo(): Any {
        val entries = prepareInput(inputList)
        val allergens: Map<Allergen, Ingredient> = basicAllergens(entries)
        return allergens.toList().sortedBy { it.first.value }.joinToString(",") { it.second.value }
    }

    private fun basicAllergens(entries: Entries): Map<Allergen, Ingredient> {
        val allergensToIngredients: Map<Allergen, Set<Ingredient>> = entries.allergensToIngredients
        return basicAllergens(allergensToIngredients)
    }

    private fun basicAllergens(input: Map<Allergen, Set<Ingredient>>, result: Map<Allergen, Ingredient> = emptyMap()): Map<Allergen, Ingredient> {
        if (input.isEmpty()) {
            return result
        }
        val singleEntry: Map.Entry<Allergen, Set<Ingredient>> = input.entries.first { it.value.size == 1 }
        if (singleEntry == null) {
            error(" Found is null Message $input, $result")
        }
        val singleAllergen: Allergen = singleEntry.key
        val singleIngredient: Ingredient = singleEntry.value.first()
        val next = result + (singleAllergen to singleIngredient)
        val nextInput: Map<Allergen, Set<Ingredient>> = (input - singleAllergen)
                .entries.map { it.key to (it.value - singleIngredient) }.toMap()
        return basicAllergens(nextInput, next)
    }


    private fun prepareInput(input: List<String>): Entries {
        return Entries(input.map { row ->
            val split: List<String> = row.split(" (contains ")
            Entry(split[0].split(" ").map { Ingredient(it) }.toSet(),
                    split[1].dropLast(1).split(", ").map { Allergen(it) }.toSet())
        })
    }
}

data class Entry(val ingredients: Set<Ingredient>, val allergens: Set<Allergen>)
data class Entries(val entries: List<Entry>) {
    val allergens = entries.flatMap { it.allergens }.toSet()
    val ingredients = entries.flatMap { it.ingredients }.toSet()

    val allergensToIngredients: Map<Allergen, Set<Ingredient>>
        get() = allergens.map {
            it to ingredientsWhichCanContain(it)
        }.toMap()

    private fun ingredientsWhichCanContain(allergen: Allergen): Set<Ingredient> {
        return entries.filter { it.allergens.contains(allergen) }
                .map { it.ingredients }
                .reduce { a, b -> a.intersect(b) }
    }

    fun safeIngredients(allergens: Map<Allergen, Ingredient>): Set<Ingredient> {
        return ingredients - allergens.values
    }
}
