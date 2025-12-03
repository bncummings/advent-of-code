import org.junit.jupiter.api.Test
import kotlin.test.assertFalse

class Day02Test {

    @Test
    fun `only works removing the correct index`() {
        assert(levelsAreSafeApartFromIndex(listOf(1, 9, 10 ,11, 12, 13), 0))
        assertFalse(levelsAreSafeApartFromIndex(listOf(1, 9, 10 ,11, 12, 13), 1))
        assertFalse(levelsAreSafeApartFromIndex(listOf(1, 9, 10 ,11, 12, 13), 3))
    }

    @Test
    fun `lambda works for list with one bad level`() {
        val list = listOf(1, 9, 10 ,11, 12, 13)
        System.out.println(list.indices.map { i -> levelsAreSafeApartFromIndex(list, i) })
        assert(list.indices.map { i -> levelsAreSafeApartFromIndex(list, i) }.any{ it })
    }

    @Test
    fun `lambda false for list with many bad levels`() {
        val list = listOf(9, 10 ,45, 45, 11, 12)
        System.out.println(list.indices.map { i -> levelsAreSafeApartFromIndex(list, i) })
        assertFalse(list.indices.map { i -> levelsAreSafeApartFromIndex(list, i) }.any{ it })
    }

    @Test
    fun `safe if no levels have to be removed`() {
        assert(levelsAreSafeBarOne(listOf(1, 3, 6 ,7, 9)))
    }

    @Test
    fun `safe if first number is out of bounds`() {
        assert(levelsAreSafeBarOne(listOf(1, 9, 10 ,11, 12, 13)))
        assert(levelsAreSafeBarOne(listOf(45, 9, 10 ,11, 12, 13)))
        assert(levelsAreSafeBarOne(listOf(1, 9, 8 ,7, 5, 3)))
        assert(levelsAreSafeBarOne(listOf(45, 9, 8 ,7, 5, 3)))
        assert(levelsAreSafeBarOne(listOf(9, 9, 8 ,7, 5, 3)))
    }

    @Test
    fun `safe if last number is out of bounds`() {
        assert(levelsAreSafeBarOne(listOf(9, 10 ,11, 12, 13, 1)))
        assert(levelsAreSafeBarOne(listOf(9, 10 ,11, 12, 13, 45)))
        assert(levelsAreSafeBarOne(listOf(9, 10 ,11, 12, 13, 13)))
        assert(levelsAreSafeBarOne(listOf(9, 8, 7, 6, 5, 45)))
        assert(levelsAreSafeBarOne(listOf(19, 18, 17, 16, 15, 1)))
    }

    @Test
    fun `safe if one number is out of bounds`() {
        assert(levelsAreSafeBarOne(listOf(9, 10 ,11, 45, 13, 14)))
        assert(levelsAreSafeBarOne(listOf(9, 10 ,11, 0, 13, 14)))
        assert(levelsAreSafeBarOne(listOf(9, 10 ,11, 11, 13, 14)))
        assert(levelsAreSafeBarOne(listOf(9, 10 ,11, 13, 13, 14)))
        assert(levelsAreSafeBarOne(listOf(8, 6, 4, 4, 1)))
        assert(levelsAreSafeBarOne(listOf(1, 3, 10, 4)))
        assert(levelsAreSafeBarOne(listOf(9, 10, 50, 11, 12)))
    }

    @Test
    fun `unsafe if all elements are the same`() {
        assertFalse(levelsAreSafeBarOne(listOf(5,5,5,5,5,5,5)))
    }

    @Test
    fun `unsafe if two repeats occur`() {
        assertFalse(levelsAreSafeBarOne(listOf(9, 10 ,11, 11, 11, 12)))
        assertFalse(levelsAreSafeBarOne(listOf(9, 10 ,10, 11, 12, 12)))
    }

    @Test
    fun `unsafe if two numbers are out of bounds`() {
        assertFalse(levelsAreSafeBarOne(listOf(9, 10 ,45, 45, 11, 12)))
        assertFalse(levelsAreSafeBarOne(listOf(9, 10 ,45, 11, 12, 50)))
        assertFalse(levelsAreSafeBarOne(listOf(9, 10 ,0, 11, 12, 0)))
        assertFalse(levelsAreSafeBarOne(listOf(9, 10 ,0, 0, 11, 12)))
    }

    @Test
    fun `unsafe if increasing then decreasing `() {
        assertFalse(levelsAreSafeBarOne(listOf(9, 10, 11, 45, 10, 9)))
        assertFalse(levelsAreSafeBarOne(listOf(9, 10, 11, 45, 44, 43)))
        assertFalse(levelsAreSafeBarOne(listOf(9, 10, 11, 11, 10, 9)))
    }

    @Test
    fun `strictly increasing then decreasing`() {
        assertFalse(levelsAreSafeBarOne(listOf(1, 2, 3, 4, 3, 2, 1)))
    }

    @Test
    fun `large valid list`() {
        val levels = (1..1000).toList()
        assert(levelsAreSafeBarOne(levels))
    }
}