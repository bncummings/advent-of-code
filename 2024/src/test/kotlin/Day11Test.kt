import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    @Test
    fun `blink turns 0 to 1 `() {
        assertEquals(
            listOf(1L),
            blink(listOf(0L))
        )
        assertEquals(
            listOf(1L, 1L, 1L),
            blink(listOf(0L, 0L, 0L))
        )
    }

    @Test
    fun `blink splits up even sized numbers`() {
        assertEquals(
            listOf(10L,0L),
            blink(listOf(1000))
        )

        assertEquals(
            listOf( 2867L, 6032L),
            blink(listOf(28676032))
        )

        assertEquals(
            listOf( 1L, 2867L, 6032L),
            blink(listOf(0, 28676032))
        )

    }

    @Test
    fun `blink multiplies other numbers by 2024`() {
        assertEquals(
            listOf(10L,0L),
            blink(listOf(1000))
        )

        assertEquals(
            listOf( 2867L, 6032L),
            blink(listOf(28676032))
        )

    }
}