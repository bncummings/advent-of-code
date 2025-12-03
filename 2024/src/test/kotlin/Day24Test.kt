import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day24Test {
    @Test
    fun `test regular expressions`() {
        assertTrue(
            EXPR_MATCHER.matches("x13 AND y13 -> sbt")
        )
    }

    @Test
    fun `getValue() returns true for a node with leaf children`() {
        val map = initialiseGraph("src/test/resources/Day24Large.txt")
        println(map["fst"])
        assertEquals(
            true,
            map["fst"]!!.generateValue()
        )
        assertEquals(
            true,
            map["nrd"]!!.generateValue()
        )
    }

    @Test
    fun `getValue() returns true for a node with opNode children`() {
        val map = initialiseGraph("src/test/resources/Day24Large.txt")
        println(map["bfw"])
        assertEquals(
            true,
            map["bfw"]!!.generateValue()
        )
    }

    @Test
    fun `get Zs returns z registers in order (small)`() {
        val map = initialiseGraph("src/test/resources/Day24Small.txt")
        println(getZs(map))
        assertEquals(
            listOf("z00", "z01", "z02"),
            getZs(map).keys.toList()
        )
    }

    @Test
    fun `get Zs returns z registers in order (large)`() {
        val map = initialiseGraph("src/test/resources/Day24Large.txt")
        println(getZs(map))
        assertEquals(
            listOf(
                "z00",
                "z01",
                "z02",
                "z03",
                "z04",
                "z05",
                "z06",
                "z07",
                "z08",
                "z09",
                "z10",
                "z11",
                "z12",
            ),
            getZs(map).keys.toList()
        )
    }

    @Test
    fun `getOutput() reads the z registers as a binary number `() {
        val map = initialiseGraph("src/test/resources/Day24Large.txt")
        assertEquals(
            2024,
            getOutput(map)
        )
    }
}