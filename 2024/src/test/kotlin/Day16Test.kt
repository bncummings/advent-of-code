import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class Day16Test {
    @Test
    fun `nodeComparator compares nodes by f value`() {
        val queue: PriorityQueue<GridNode> = PriorityQueue(nodeComparator)

        queue.add(GridNode(
            Pair(0,0),
            0,
            10,
            null
        ))
        queue.add(GridNode(
            Pair(0,0),
            0,
            0,
            null
        ))
        queue.add(GridNode(
            Pair(0,0),
            0,
            5,
            null
        ))

        assertEquals(
           0,
            queue.poll().heuristic
        )
        assertEquals(
            5,
            queue.poll().heuristic
        )
        assertEquals(
            10,
            queue.poll().heuristic
        )
    }

    @Test
    fun `getShortestPath returns the shortest path`() {
        val grid = getQuestionSixInput("src/test/resources/Day16Small.txt")
        //val path = getShortestPath(grid)!!.reversed()

        assertEquals(
            7036,
            calculateScore(grid)
        )
    }
}