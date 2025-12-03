import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day21Test {
    @Test
    fun `getMinSequence returns a sequence which connects two points`() {
        assertEquals(
            "v>A",
            getMinSequence(
                Pair(0,0),
                listOf(Pair(1,1))
            )
        )

        assertEquals(
                "vv>>A",
            getMinSequence(
                Pair(0,0),
                listOf(Pair(2,2))
            )
        )

        assertEquals(
            "<<^^A",
            getMinSequence(
                Pair(2,2),
                listOf(Pair(0,0))
            )
        )
    }

    @Test
    fun `getMinSequence returns a sequence which connects many points`() {
        assertEquals(
            ">>vvA>A<vvvA",
            getMinSequence(
                Pair(0,0),
                listOf(Pair(2,2), Pair(2,3), Pair(5,2))
            )
        )
    }

    /* I rearranged some of the orders of directions inbetween A presses from the test data,
    they are still equivalent solutions */
    @Test
    fun `returns a sequence from a code`() {
        assertEquals(
            "<A^A>^^AvvvA",
            getSequence("029A", numKeypadMap)
        )
        assertEquals(
            "<<vA>>^A<A>AvA<^AA>A<vAAA>^A",
            getSequence("<A^A>^^AvvvA", dirKeypadMap)
        )
        assertEquals(
            "<vA<AA>>^AvAA<^A>A<<vA>>^AvA^A<vA>^A<<vA>^A>AAvA^A<<vA>A>^AAAvA<^A>A",
            getSequence("v<<A>>^A<A>AvA<^AA>A<vAAA>^A", dirKeypadMap)
        )
    }

    @Test
    fun `complexity calculation`() {
        assertEquals(
            1972,
            calculateComplexity("029A")
        )
        assertEquals(
            60 * 980,
            calculateComplexity("980A")
        )
        assertEquals(
            68 * 179,
            calculateComplexity("179A")
        )
        assertEquals(
            64 * 456,
            calculateComplexity("456A")
        )
        assertEquals(
            64 * 379,
            calculateComplexity("379A")
        )
    }
}