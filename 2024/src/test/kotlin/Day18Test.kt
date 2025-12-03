import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day18Test {
    @Test
    fun `small`() {
        val coords = question18("src/test/resources/Day18Small.txt")
        val grid = initialiseGrid(7, coords.take(12))
        println(grid)
        println(getShortestPath(grid))
        assertEquals(
            22,
            getShortestPath(grid)!!.size - 1
        )

    }
}