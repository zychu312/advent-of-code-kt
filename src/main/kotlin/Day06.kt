class Day06 {
    private fun solveWithMarkerSize(markerSize: Int): Int {
        return loadFile("Day06Input.txt")
            .readText()
            .withIndex()
            .windowed(markerSize, partialWindows = true)
            .first { indexedValues -> indexedValues.map { it.value }.toSet().size == markerSize }
            .last().index + 1
    }

    fun solve() {
        listOf(4, 14)
            .map(::solveWithMarkerSize)
            .forEachIndexed { i, firstSignificantCharacter -> println("Problem ${i + 1}: $firstSignificantCharacter") }
    }
}


fun main() = Day06().solve()