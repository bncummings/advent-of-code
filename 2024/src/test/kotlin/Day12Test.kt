import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day12Test {

    @Test
    fun `single plot test`() {
        val grid = listOf(
            "AA",
            "AA"
        ).map { it.toList() }

        assertEquals(
            32,
            calculateTotalPrice(grid)
        )
    }

    @Test
    fun `identical plot test`() {
        val grid = listOf(
            "AABB",
            "AABB"
        ).map { it.toList() }

        assertEquals(
            64,
            calculateTotalPrice(grid)
        )
    }

    @Test
    fun `smallFarm test`() {
        val grid = listOf(
            "AAAA",
            "BBCD",
            "BBCC",
            "EEEC"
        ).map { it.toList() }

        assertEquals(
            140,
            calculateTotalPrice(grid)
        )
    }

    @Test
    fun `large farm test`() {
        val grid = listOf(
            "RRRRIICCFF",
            "RRRRIICCCF",
            "VVRRRCCFFF",
            "VVRCCCJFFF",
            "VVVVCJJCFE",
            "VVIVCCJJEE",
            "VVIIICJJEE",
            "MIIIIIJJEE",
            "MIIISIJEEE",
            "MMMISSJEEE"
        ).map { it.toList() }

        assertEquals(
            1930,
            calculateTotalPrice(grid)
        )
    }
}