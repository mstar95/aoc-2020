package util

fun groupByEmpty(inputList: List<String>, aggregate: List<String> = emptyList()): List<List<String>> {
    if (inputList.isEmpty()) {
        return emptyList()
    }
    val head = inputList.head
    return if (head.isBlank()) listOf(aggregate) + groupByEmpty(inputList.tail, emptyList())
    else groupByEmpty(inputList.tail, aggregate + head)
}

private val <E> List<E>.head: E
    get() = first()

private val <E> List<E>.tail: List<E>
    get() = drop(1)