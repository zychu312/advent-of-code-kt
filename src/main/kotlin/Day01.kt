interface Caloric {
    val caloricIntake: Int
}

data class Item(override val caloricIntake: Int) : Caloric

data class Elf(val id: Int, val items: List<Item>) {
    val totalCalories = items.sumOf { it.caloricIntake }
}

tailrec fun parseInputToElves(input: List<String>, acc: List<Elf> = emptyList()): List<Elf> {

    if (input.isEmpty()) {
        return acc
    }

    val elf = input
        .takeWhile(String::isNotBlank)
        .map(String::toInt)
        .map { calories -> Item(calories) }
        .let { items -> Elf(id = acc.size + 1, items) }

    return parseInputToElves(input.drop(elf.items.size + 1), acc + elf)

}

fun main() {

    val elves = parseInputToElves(loadFile("Day01Input.txt").readLines())

    val maxCaloriesInInventory = elves.maxOf { elf -> elf.totalCalories }

    println("Part 1: $maxCaloriesInInventory")

    val sumOfTopThreeMostCaloricInventories = elves
        .map { it.totalCalories }
        .sortedDescending()
        .take(3)
        .sum()

    println("Part 2: $sumOfTopThreeMostCaloricInventories")
}
