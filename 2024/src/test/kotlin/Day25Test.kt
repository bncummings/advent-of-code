import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day25Test {
    @Test
    fun `keyFitsLock() returns true for compatible keys`() {
        assertTrue(
            keyFitsLock(mutableListOf(0,5,3,4,3), mutableListOf(3,0,2,0,1))
        )
    }

    @Test
    fun `countCombinations() returns 1 for single compatible pair`() {
        assertEquals(
            1,
            countCombinations(listOf(mutableListOf(0,5,3,4,3)), listOf(mutableListOf(3,0,2,0,1)))
        )
    }
}