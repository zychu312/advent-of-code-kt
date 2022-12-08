fun Char.toPriority(): Int = when (this.category) {
    CharCategory.UPPERCASE_LETTER -> this.code - 'A'.code + 'z'.toPriority() + 1
    CharCategory.LOWERCASE_LETTER -> this.code - 'a'.code + 1
    else -> error("Wrong letter")
}


infix fun String.toCompartment(range: IntRange) = this.substring(range).toCharArray().toSet()

fun loadInventories() = loadFile("Day03Input.txt").readLines()

fun main() {

    loadInventories()
        .sumOf { line ->
            val halfIndex = line.length / 2

            val firstCompartment = line toCompartment (0 until halfIndex)
            val secondCompartment = line toCompartment (halfIndex until line.length)

            val itemsOccurringMultipleTimes = firstCompartment.intersect(secondCompartment)

            itemsOccurringMultipleTimes.sumOf {
                it.toPriority()
            }
        }.let { println("Problem one: $it") }


    loadInventories()
        .asSequence()
        .chunked(3)
        .map { strings -> strings.map { it.toCharArray().toSet() } }
        .map { threeInventories -> threeInventories.reduce { a, b -> a intersect b } }
        .map { intersectionChars -> intersectionChars.first() }
        .sumOf { uniqueItem -> uniqueItem.toPriority() }
        .let { println("Problem two: $it") }

}
