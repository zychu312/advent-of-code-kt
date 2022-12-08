import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class Day03 {
    @TestFactory
    fun verifyCharToPriorityMapping() = listOf(
        'a' to 1,
        'z' to 26,
        'A' to 27,
        'Z' to 52,
        'p' to 16,
        'L' to 38
    ).map { (letter, expectedPriority) ->
        DynamicTest.dynamicTest(letter.toString()) {
            assertEquals( expectedPriority, letter.toPriority())
        }
    }
}