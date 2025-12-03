import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day19Test {
    @Test
    fun `single false case`() {
        assertEquals(
            emptyList(),
            getPossibleDesigns(
                Day19(
                    setOf("ab", "bc", "ca"),
                    listOf("c")
                )
            )
        )
    }

    @Test
    fun `example test cases`() {
        val input = Day19(
            setOf("r", "wr", "b", "g", "bwu", "rb", "gb", "br"),
            listOf(
                "brwrr",
                "bggr",
                "gbbr",
                "rrbgbr",
                "ubwu",
                "bwurrg",
                "brgr",
                "bbrgwb"
            )
        )

        assertEquals(
            listOf("brwrr",
                "bggr",
                "gbbr",
                "rrbgbr",
                "bwurrg",
                "brgr"),
            getPossibleDesigns(input)
        )
    }

    @Test
    fun `handles large input without timing out`() {
        val input = Day19.fromFile("src/main/resources/Day19.txt")
        input.designs = listOf("bwbbbuwrgggubwwrbgugguuurbbwwrbwwuwwugwuuwu")

        assertEquals(
            emptyList(),
            getPossibleDesigns(input)
        )
    }
}