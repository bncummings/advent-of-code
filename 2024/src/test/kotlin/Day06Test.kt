import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day06Test {
    @Test
    fun `turn90Degrees changes direction successfully`() {
        val guard = Guard(Pair(0,0), Direction.UP)
        guard.turn90Degrees()
        assertEquals( Direction.RIGHT, guard.direction)
        guard.turn90Degrees()
        assertEquals( Direction.DOWN, guard.direction)
        guard.turn90Degrees()
        assertEquals( Direction.LEFT, guard.direction)
        guard.turn90Degrees()
        assertEquals( Direction.UP, guard.direction)
    }

    @Test
    fun `onEdge returns true if guard is at the top and facing up`() {
        val grid = MutableList(8) {MutableList(8) {' '} }

        val guard = Guard(Pair(0,0), Direction.UP)
        assertTrue(onEdge(grid, guard))
        guard.move(0, 1)
        assertTrue(onEdge(grid, guard))

        guard.direction = Direction.DOWN
        assertFalse(onEdge(grid, guard))
        guard.move(0, 0)
        assertFalse(onEdge(grid, guard))
    }

    @Test
    fun `onEdge returns true if guard is at the bottom and facing down`() {
        val grid = MutableList(8) {MutableList(8) {' '} }

        val guard = Guard(Pair(7,0), Direction.DOWN)
        assertTrue(onEdge(grid, guard))
        guard.move(7, 1)
        assertTrue(onEdge(grid, guard))

        guard.direction = Direction.UP
        assertFalse(onEdge(grid, guard))
        guard.move(7, 0)
        assertFalse(onEdge(grid, guard))
    }

    @Test
    fun `onEdge returns true if guard is at the left and facing left`() {
        val grid = MutableList(8) {MutableList(8) {' '} }

        val guard = Guard(Pair(0,0), Direction.LEFT)
        assertTrue(onEdge(grid, guard))
        guard.move(1, 0)
        assertTrue(onEdge(grid, guard))

        guard.direction = Direction.RIGHT
        assertFalse(onEdge(grid, guard))
        guard.move(7, 0)
        assertFalse(onEdge(grid, guard))
    }

    @Test
    fun `onEdge returns true if guard is at the right and facing right`() {
        val grid = MutableList(8) {MutableList(8) {' '} }

        val guard = Guard(Pair(0,7), Direction.RIGHT)
        assertTrue(onEdge(grid, guard))
        guard.move(1, 7)
        assertTrue(onEdge(grid, guard))

        guard.direction = Direction.LEFT
        assertFalse(onEdge(grid, guard))
        guard.move(7, 7)
        assertFalse(onEdge(grid, guard))
    }

    @Test
    fun `onEdge returns false if guard in the middle`() {
        val grid = MutableList(8) {MutableList(8) {' '} }

        val guard = Guard(Pair(6,6), Direction.RIGHT)
        assertFalse(onEdge(grid, guard))
        guard.direction = Direction.LEFT
        assertFalse(onEdge(grid, guard))
        guard.direction = Direction.UP
        assertFalse(onEdge(grid, guard))
        guard.direction = Direction.DOWN
        assertFalse(onEdge(grid, guard))
    }

    @Test
    fun `move adds previous position to set`() {
        val guard = Guard(Pair(0,0), Direction.UP)
        guard.move(0, 1)
        guard.move(1, 0)

        assertEquals(
            hashSetOf(Pair(0,0), Pair(1,0), Pair(0,1)),
            guard.visitedSet
        )
    }

    @Test
    fun `another test case`() {
        val grid = listOf(
            listOf('.', '.', '.', '.', '.'),
            listOf('.', '.', '#', '.', '.'),
            listOf('#', '.', '^', '.', '#'),
            listOf('.', '.', '#', '.', '.'),
            listOf('.', '.', '.', '#', '.')
        )

        val guard = Guard(findGuard(grid)!!, Direction.UP)

        assertEquals(
            Guard(Pair(2,2), Direction.UP),
            guard
        )

        patrol(grid, guard)
        System.out.println(guard.visitedSet)

        assertEquals(
            5,
            guard.visitedSet.size
        )
    }

    @Test
    fun `guard repeats its tracks`() {
        val grid = listOf(
            listOf('.', '.', '.', '.', '.'),
            listOf('.', '.', '#', '.', '.'),
            listOf('.', '.', '^', '.', '#'),
            listOf('.', '.', '.', '#', '.'),
            listOf('.', '.', '.', '.', '.')
        )

        val guard = Guard(findGuard(grid)!!, Direction.UP)

        assertEquals(
            Guard(Pair(2,2), Direction.UP),
            guard
        )

        patrol(grid, guard)
        System.out.println(guard.visitedSet)

        assertEquals(
            4,
            guard.visitedSet.size
        )
    }

    @Test
    fun `test case no obsticals`() {
        val grid = listOf(
            listOf('.', '.', '.', '.', '#'),
            listOf('.', '.', '.', '#', '.'),
            listOf('.', '.', '^', '.', '.'),
            listOf('.', '.', '.', '.', '.'),
            listOf('.', '.', '.', '.', '.')
        )

        val guard = Guard(findGuard(grid)!!, Direction.UP)

        assertEquals(
            Guard(Pair(2,2), Direction.UP),
            guard
        )

        patrol(grid, guard)
        System.out.println(guard.visitedSet)

        assertEquals(
            3,
            guard.visitedSet.size
        )
    }

    @Test
    fun `patrol terminates when the guard leaves and collects the correct number of visited points`() {
        val grid = listOf(
            listOf('.', '.', '.', '.', '#', '.', '.', '.', '.', '.'),
            listOf('.', '.', '.', '.', '.', '.', '.', '.', '.', '#'),
            listOf('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
            listOf('.', '.', '#', '.', '.', '.', '.', '.', '.', '.'),
            listOf('.', '.', '.', '.', '.', '.', '.', '#', '.', '.'),
            listOf('.', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
            listOf('.', '#', '.', '.', '^', '.', '.', '.', '.', '.'),
            listOf('.', '.', '.', '.', '.', '.', '.', '.', '#', '.'),
            listOf('#', '.', '.', '.', '.', '.', '.', '.', '.', '.'),
            listOf('.', '.', '.', '.', '.', '.', '#', '.', '.', '.')
        )

        val guard = Guard(findGuard(grid)!!, Direction.UP)

        assertEquals(
            Guard(Pair(6,4), Direction.UP),
            guard
        )

        patrol(grid, guard)
        System.out.println(guard.visitedSet)

        assertEquals(
            41,
            guard.visitedSet.size
        )
    }

    @Test
    fun `patrol detects infinite loops and returns false`() {
        val grid = listOf(
            listOf('.', '.', '.', '.', '.'),
            listOf('.', '.', '#', '.', '.'),
            listOf('.', '#', '^', '#', '#'),
            listOf('.', '.', '#', '#', '.'),
            listOf('.', '.', '.', '.', '.')
        )

        val guard = Guard(findGuard(grid)!!, Direction.UP)

        assertEquals(
            Guard(Pair(2,2), Direction.UP),
            guard
        )

        assertFalse(patrol(grid, guard))
    }
}