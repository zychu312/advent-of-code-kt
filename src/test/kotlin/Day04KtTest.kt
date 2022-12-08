import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class Day04KtTest {

    @Test
    fun contains() {
        assertTrue { 2..8 contains 3..7 }
        assertTrue { 1..1 contains  1..1 }
        assertFalse { 10..20 contains 18..22}
    }
}