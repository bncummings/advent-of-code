import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day07Test {
    @Test
    fun `isValid returns true if and only if there is a permutation of sum and mul`() {
        assertTrue(
            isValid(TestData(190, listOf(10, 19)))
        )
        assertTrue(
            isValid(TestData(29, listOf(10, 19)))
        )
        assertTrue(
            isValid(TestData(3267, listOf(81, 40, 27)))
        )

        assertFalse(
            isValid(TestData(21037, listOf(9, 7, 18, 13)))
        )
    }

    @Test
    fun `sums valid calibration numbers`() {
        assertEquals(
            3749,
            countValidTests(
                listOf(
                    TestData(190, listOf(10, 19)),
                    TestData(3267, listOf(81, 40, 27)),
                    TestData(83, listOf(17, 5)),
                    TestData(156, listOf(15, 6)),
                    TestData(7290, listOf(6, 8, 6, 15)),
                    TestData(161011, listOf(16, 10, 13)),
                    TestData(192, listOf(17, 8, 14)),
                    TestData(21037, listOf(9, 7, 18, 13)),
                    TestData(292, listOf(11, 6, 16, 20))
                )
            )
        )
    }
    // 1. Empty List
    @Test
    fun testEmptyList() {
        assertEquals(true, isValid(TestData(0, emptyList())), "Empty list with target 0 should return true")
        assertEquals(false, isValid(TestData(5, emptyList())), "Empty list with non-zero target should return false")
    }

    // 4. All Elements Are Zero
    @Test
    fun testAllElementsZero() {
        assertEquals(true, isValid(TestData(0, listOf(0, 0, 0))), "List of zeros should satisfy target 0")
        assertEquals(false, isValid(TestData(5, listOf(0, 0, 0))), "List of zeros cannot reach non-zero target")
    }

    // 5. Pruning Condition
    @Test
    fun testPruningCondition() {
        assertEquals(false, isValid(TestData(100, listOf(2, 3, 4))), "Target too large to reach")
    }

    // 6. Large Numbers
    @Test
    fun testLargeNumbers() {
        assertEquals(true, isValid(TestData(2_000_000, listOf(1_000_000, 2))), "Large multiplication should work")
        assertEquals(true, isValid(TestData(1L * Int.MAX_VALUE + 2, listOf(Int.MAX_VALUE, 2))), "Large addition should work")
        assertEquals(false, isValid(TestData(Long.MAX_VALUE, listOf(Int.MAX_VALUE, 2))), "Unreachable target with large numbers")
    }

    // 8. Impossible Target
    @Test
    fun testImpossibleTarget() {
        assertEquals(false, isValid(TestData(100, listOf(1, 2, 3))), "Target 100 cannot be reached")
        assertEquals(false, isValid(TestData(15, listOf(2, 4, 8))), "No valid operations result in target 15")
    }
}