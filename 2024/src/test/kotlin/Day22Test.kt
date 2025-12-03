import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Test {
    @Test
    fun `prune works accurately and with no negatives`() {
        assertEquals(
            16113920, prune(100000000)
        )
    }

    @Test
    fun `mix works accurately`() {
        assertEquals(
            37, mix(42, 15)
        )
    }

    @Test
    fun `calculateSecret on one iteration`() {
        assertEquals(
            15887950, calculateSecret(123)
        )
    }

    @Test
    fun `calculateSecret on 2000 iterations`() {
        assertEquals(
            8685429, calculateNthSecret(1, 2000)
        )
    }
}