import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14Test {
    @Test
    fun `getQuadrants splits the grid into its 4 quarters`() {
        var grid = listOf(
            listOf(1,0,1),
            listOf(0,0,0),
            listOf(1,0,1)
        )

        assertEquals(
            listOf(
                listOf(listOf(1)), listOf(listOf(1)), listOf(listOf(1)), listOf(listOf(1))
            ),
            getQuadrants(grid)
        )

        grid = listOf(
            listOf(0,0,0,0,0,0,2,0,0,1,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(1,0,0,0,0,0,0,0,0,0,0),
            listOf(0,1,1,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,0,1,0,0,0,0,0),
            listOf(0,0,0,1,2,0,0,0,0,0,0),
            listOf(0,1,0,0,0,0,1,0,0,0,0),
        )

        assertEquals(
            listOf(
                listOf(
                    listOf(0,0,0,0,0),
                    listOf(0,0,0,0,0),
                    listOf(1,0,0,0,0),
                ),
                listOf(
                    listOf(2,0,0,1,0),
                    listOf(0,0,0,0,0),
                    listOf(0,0,0,0,0),
                ),
                listOf(
                    listOf(0,0,0,0,0),
                    listOf(0,0,0,1,2),
                    listOf(0,1,0,0,0),
                ),
                listOf(
                    listOf(0,0,0,0,0),
                    listOf(0,0,0,0,0),
                    listOf(1,0,0,0,0),
                )
            ),
            getQuadrants(grid)
        )
    }

    @Test
    fun `sumElementsInGrid() returns the sum of each element`() {
        assertEquals(
            1,
            sumElementsInGrid(
                listOf(
                    listOf(0,0,0,0,0),
                    listOf(0,0,0,0,0),
                    listOf(1,0,0,0,0),
                )
            )
        )
        assertEquals(
            4,
            sumElementsInGrid(
                listOf(
                    listOf(0,0,0,0,0),
                    listOf(0,0,0,1,2),
                    listOf(0,1,0,0,0),
                )
            )
        )
        assertEquals(
            3,
            sumElementsInGrid(
                listOf(
                    listOf(2,0,0,1,0),
                    listOf(0,0,0,0,0),
                    listOf(0,0,0,0,0),
                )
            )
        )
    }

    @Test
    fun `calculateSafetyFactor() multiplies the sum of each quadratn`() {
        val grid = listOf(
            listOf(0,0,0,0,0,0,2,0,0,1,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(1,0,0,0,0,0,0,0,0,0,0),
            listOf(0,1,1,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,0,1,0,0,0,0,0),
            listOf(0,0,0,1,2,0,0,0,0,0,0),
            listOf(0,1,0,0,0,0,1,0,0,0,0),
        )

        assertEquals(
            12,
            calculateSafetyFactor(grid)
        )
    }

    @Test
    fun `grid after 1 robot() after 1 second`() {
        val grid = listOf(
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,1,0,0,0,0,0,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
        )

        assertEquals(
            grid,
            gridAfterNSeconds(7,11, 1,
                listOf(Robot(
                    Pair(4,2), Pair(-3,2)
                ))
            )
        )
    }

    @Test
    fun `grid after 1 robot() wrap-around`() {
        val grid = listOf(
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
            listOf(0,0,0,0,0,0,1,0,0,0,0),
            listOf(0,0,0,0,0,0,0,0,0,0,0),
        )

        assertEquals(
            grid,
            gridAfterNSeconds(7,11, 2,
                listOf(Robot(
                    Pair(4,2), Pair(-3,2)
                ))
            )
        )
    }

}