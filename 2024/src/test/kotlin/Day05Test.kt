import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day05Test {
    var testRules = listOf(
        Pair(47,53),
        Pair(97,13),
        Pair(97,61),
        Pair(97,47),
        Pair(75,29),
        Pair(61,13),
        Pair(75,53),
        Pair(29,13),
        Pair(97,29),
        Pair(53,29),
        Pair(61,53),
        Pair(97,53),
        Pair(61,29),
        Pair(47,13),
        Pair(75,47),
        Pair(97,75),
        Pair(47,61),
        Pair(75,61),
        Pair(47,29),
        Pair(75,13),
        Pair(53,13)
    )

    @Test
    fun `countPairsStartingWith counts all the pairs staring with a given value`() {
        val pairs = listOf(
            Pair(1,2),
            Pair(1,3),
            Pair(1,4),
            Pair(2,2),
        )

        assertEquals(3, countPairsStartingWith(1, pairs, listOf(1,2,3,4)))
    }

    @Test
    fun `getMiddleNumber returns the number with the middle number of pairs`() {
        assertEquals(
            2,
            getMiddleNumber(
                listOf(1,2,3),
                listOf(Pair(1,2), Pair(1,3), Pair(2,3))
            )
        )
    }

    @Test
    fun `reject updates in the wrong order`() {
        assertTrue(
            inCorrectOrder(
                listOf(75,47,61,53,29),
                testRules
            )
        )

        assertFalse(
            inCorrectOrder(
                listOf(97,13,75,29,47),
                testRules
            )
        )
    }

    @Test
    fun `only counts the number of pairs where the second number is included in updates`() {
        assertEquals(
            61,
            getMiddleNumber(
                listOf(75,47,61,53,29),
                testRules
            )
        )
        assertEquals(
            53,
            getMiddleNumber(
                listOf(97,61,53,29,13),
                testRules
            )
        )
        assertEquals(
            29,
            getMiddleNumber(
                listOf(75,29,13),
                testRules
            )
        )
    }


    @Test
    fun `can map from an update list to a list of middle values`() {
        assertEquals(
            listOf(61),
            getMiddleNumbersFromOrderedUpdates(
                QuestionFiveInput(
                    testRules.toMutableList(),
                    mutableListOf(
                        listOf(75,47,61,53,29),
                    )
                )
            )
        )
    }

    @Test
    fun ` getMiddleNumbersFromInput ignores out of order lists`() {
        assertEquals(
            listOf(61, 53),
            getMiddleNumbersFromOrderedUpdates(
                QuestionFiveInput(
                    testRules.toMutableList(),
                    mutableListOf(
                        listOf(75,47,61,53,29),
                        listOf(97,13,75,29,47),
                        listOf(97,61,53,29,13)

                    )
                )
            )
        )
    }

    @Test
    fun ` getMiddleNumbersFromUnorderedList ignores ordered lists`() {
        assertEquals(
            listOf(47),
            getMiddleNumbersFromUnorderedUpdates(
                QuestionFiveInput(
                    testRules.toMutableList(),
                    mutableListOf(
                        listOf(75,47,61,53,29),
                        listOf(97,13,75,29,47),
                        listOf(97,61,53,29,13)
                    )
                )
            )
        )
    }

    @Test
    fun `can sum correctly a list of middle values`() {
        assertEquals(
            143,
            sumMiddleNumbers(
                QuestionFiveInput(
                    testRules.toMutableList(),
                    mutableListOf(
                        listOf(75,47,61,53,29),
                        listOf(97,61,53,29,13),
                        listOf(75,29,13)
                    )
                )
            )
        )
    }
}
