import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Test {
    @Test
    fun `sumTrailheads() performs a topological dfs on a given grid`() {
        val grid = listOf(
            listOf(8,9,0,1,0,1,2,3),
            listOf(7,8,1,2,1,8,7,4),
            listOf(8,7,4,3,0,9,6,5),
            listOf(9,6,5,4,9,8,7,4),
            listOf(4,5,6,7,8,9,0,3),
            listOf(3,2,0,1,9,0,1,2),
            listOf(0,1,3,2,9,8,0,1),
            listOf(1,0,4,5,6,7,3,2)
        )

        assertEquals(
            36,
            sumTrailheads(grid)
        )
    }

    @Test
    fun `sumTrailheadScores() performs a topological dfs on a given grid`() {
        val grid = listOf(
            listOf(8,9,0,1,0,1,2,3),
            listOf(7,8,1,2,1,8,7,4),
            listOf(8,7,4,3,0,9,6,5),
            listOf(9,6,5,4,9,8,7,4),
            listOf(4,5,6,7,8,9,0,3),
            listOf(3,2,0,1,9,0,1,2),
            listOf(0,1,3,2,9,8,0,1),
            listOf(1,0,4,5,6,7,3,2)
        )

        assertEquals(
            81,
            sumTrailheadScores(grid)
        )
    }

    @Test
    fun `sumTrailheadScores() gives a score equal to the number 'non distinct' 9s it can reach`() {
        val grid = listOf(
            listOf(-1,-1,-1,-1, 0,-1),
            listOf(-1, 4, 3, 2, 1,-1),
            listOf(-1, 5,-1,-1, 2,-1),
            listOf(-1, 6, 5, 4, 3,-1),
            listOf(-1, 7,-1,-1, 4,-1),
            listOf(-1, 8, 7, 6, 5,-1),
            listOf(-1, 9,-1,-1,-1,-1),
        )

        assertEquals(
            3,
            sumTrailheadScores(grid)
        )
    }
}