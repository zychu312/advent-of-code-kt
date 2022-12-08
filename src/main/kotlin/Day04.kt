infix fun IntRange.contains(another: IntRange) = this.first <= another.first && this.last >= another.last
infix fun IntRange.overlaps(another: IntRange) = (this intersect another).isNotEmpty()

fun main() {

    val ranges = loadFile("Day04Input.txt")
        .readLines()
        .map { line -> line.split(',', '-').map(String::toInt) }
        .map { (oneStart, oneEnd, twoStart, twoEnd) -> oneStart..oneEnd to twoStart..twoEnd }


    ranges
        .count { (rangeOne, rangeTwo) -> (rangeOne contains rangeTwo) or (rangeTwo contains rangeOne) }
        .let { println("Problem 1: $it") }

    ranges
        .count { (rangeOne, rangeTwo) -> rangeOne overlaps rangeTwo }
        .let { println("Problem 2: $it") }

}