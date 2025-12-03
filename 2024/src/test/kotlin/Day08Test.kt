import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Test {
    val grid = listOf(
        listOf('.', '.', '.', '.', '.'),
        listOf('.', '.', '#', '.', '.'),
        listOf('.', '#', '^', '#', '#'),
        listOf('.', '.', '#', '#', '.'),
        listOf('.', '.', '.', '.', '.')
    )

    @Test
    fun `calculate vector`() {
        assertEquals(
            Pair(1,1),
            calculateVector(
                Pair(1,1),
                Pair(2,2)
            )
        )

        assertEquals(
            Pair(-1,-1),
            calculateVector(
                Pair(2,2),
                Pair(1,1)
            )
        )
    }

    @Test
    fun `a`() {
        val grid = listOf(
            listOf('.', '.', '.', '.', '.'),
            listOf('.', 'a', '.', '.', '.'),
            listOf('.', '.', 'a', '.', '.'),
            listOf('.', '.', '.', '.', '.'),
            listOf('.', '.', '.', '.', '.')
        )

        generateAntiNodesForAntenna(Pair(1,1), grid)

        assertEquals(
            setOf(Pair(0,0), Pair(3,3)),
            antiNodeSet
        )
    }
}