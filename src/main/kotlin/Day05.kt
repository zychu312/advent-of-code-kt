import java.util.Stack


class Day05 {
    data class MoveOperation(val howMany: Int, val fromStack: Int, val toStack: Int)

    private fun parseStacks(allLines: List<String>): List<Stack<Char>> {
        val stacksInput = allLines
            .takeWhile { !it.trim().startsWith("1") }
            .map { line ->
                line
                    .chunked(4)
                    .map { it.trim() }
                    .map { it.ifBlank { null } }
                    .map { it?.find { c -> c.isLetter() } }
            }

        return buildList {

            val noOfStacks = stacksInput.first().size

            repeat(noOfStacks) {
                this.add(Stack())
            }

            stacksInput
                .asReversed()
                .forEach { line ->
                    line.forEachIndexed { stackIndex, char ->
                        if (char != null) this[stackIndex].push(char)
                    }
                }
        }
    }

    private fun parseOperations(allLines: List<String>): List<MoveOperation> {
        return allLines
            .dropWhile { line -> !line.trim().startsWith("move") }
            .map { line ->
                line
                    .split(" ")
                    .map { it.trim() }
                    .filterIndexed { index, s -> index % 2 == 1 }
                    .map { str -> str.toInt() }
            }
            .map { (first, second, third) -> MoveOperation(howMany = first, second - 1, third - 1) }
    }


    private fun parseData(allLines: List<String>) = parseStacks(allLines) to parseOperations(allLines)

    private fun interface Strategy {
        fun solve(stacks: List<Stack<Char>>, operations: List<MoveOperation>): Unit
    }

    private val problemOneStrategy = Strategy { stacks, operations ->
        operations.forEach { (howMany, from, to) ->
            repeat(howMany) {
                stacks[to].push(stacks[from].pop())
            }
        }
    }

    private val problemTwoStrategy = Strategy { stacks, operations ->
        operations.forEach { (howMany, from, to) ->

            val temp = Stack<Char>()

            repeat(howMany) {
                temp.push(stacks[from].pop())
            }

            while (temp.isNotEmpty()) {
                stacks[to].push(temp.pop())
            }
        }
    }

    private fun solveUsing(strategy: Strategy): String {
        val (stacks, operations) = loadFile("Day05Input.txt")
            .readLines()
            .let(::parseData)

        strategy.solve(stacks, operations)

        return stacks
            .map { stack -> stack.peek() }
            .joinToString("")
    }

    fun solve() {
        listOf(problemOneStrategy, problemTwoStrategy)
            .map(::solveUsing)
            .forEachIndexed { i, s -> println("Problem ${i + 1}: $s") }
    }


}

fun main() {
    Day05().solve()
}