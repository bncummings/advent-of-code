import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day13Test {
    @Test
    fun `determinant calculates the determinant of a 2x2 matrix`() {
        assertEquals(
            1.0,
            determinant(
                listOf(
                    listOf(1.0, 0.0),
                    listOf(0.0, 1.0)
                )
            )
        )

        assertEquals(
            4.0,
            determinant(
                listOf(
                    listOf(2.0, 1.0),
                    listOf(0.0, 2.0)
                )
            )
        )
    }

    @Test
    fun `invertMatrix doesn't affect the identity`() {
        assertEquals(
            listOf(
                listOf(1.0, 0.0),
                listOf(0.0, 1.0)
            ),
            invertMatrix(
                listOf(
                    listOf(1.0, 0.0),
                    listOf(0.0, 1.0)
                )
            )
        )

        assertEquals(
            listOf(
                listOf(0.6, -0.7),
                listOf(-0.2, 0.4)
            ),
            invertMatrix(
                listOf(
                    listOf(4.0, 7.0),
                    listOf(2.0, 6.0)
                )
            )
        )
    }

    @Test
    fun `calculateButtonPresses() `() {
        val claw = ClawMachine(
            Vector(94.0, 34.0),
            Vector(22.0, 67.0),
            Vector(8400.0, 5400.0),
        )

        assertEquals(
            Vector(80.0, 40.0),
            claw.calculateButtonPresses()
        )
    }

    @Test
    fun `calculateTokens() `() {
        val claw = ClawMachine(
            Vector(94.0, 34.0),
            Vector(22.0, 67.0),
            Vector(8400.0, 5400.0),
        )

        assertEquals(
            280,
            claw.calculateTokens()
        )
    }
}