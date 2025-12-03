import java.io.File
import java.util.*
import kotlin.math.abs

/**
 * Represents a node in the grid for A* pathfinding.
 * @property pos The position of the node as a pair of integers
 * @property pathLength The total cost of reaching this node
 * @property heuristic The estimated cost from this node to the goal
 * @property parent The parent node in the path
 */
data class GridNode(
    val pos: Pair<Int, Int>,
    val pathLength: Int,
    val heuristic: Int,
    val parent: GridNode?
)

/**
 * Comparator for sorting GridNodes based on their total cost (path length + heuristic).
 */
val nodeComparator = compareBy<GridNode> { it.pathLength + it.heuristic }

/**
 * Calculates the Manhattan distance between two positions.
 * @param a The first position
 * @param b The second position
 * @return The Manhattan distance between the two positions
 */
fun manhattan(a: Pair<Int, Int>, b: Pair<Int, Int>): Int =
    abs(a.first - b.first) + abs(a.second - b.second)


/**
 * Finds the shortest path between start and end positions in a grid.
 * @param grid The grid representing the environment
 * @return The shortest path as a list of coordinates, or null if no path exists
 */
fun getShortestPath(grid:List<List<Char>>, startPos:Pair<Int,Int>, endPos:Pair<Int,Int>):List<Pair<Int, Int>>? {
    val closedList: MutableList<GridNode> = mutableListOf()
    val openList: PriorityQueue<GridNode> = PriorityQueue(nodeComparator)

    /* add the start node to the queue */
    openList.add(GridNode(
        startPos,
        0,
        0,
        null
    ))

    while(openList.isNotEmpty()) {
        val currentNode = openList.poll()
        closedList.add(currentNode)

        /* if we've found the end position, backtrack to the start */
        if(currentNode.pos == endPos) {
            val path: MutableList<Pair<Int, Int>> = mutableListOf()
            var current = currentNode
            while(current != null) {
                path.add(current.pos)
                current = current.parent
            }
            return path.reversed()
        }

        /* add all the edge nodes*/
        for (childPos in getSurrounding(currentNode.pos.first, currentNode.pos.second, grid)) {
            /* if it's a block */
            if(grid[childPos.first][childPos.second] == '#') {
                continue
            }
            /* if child is already on the closed list */
            if(closedList.any { it.pos == childPos }) {
                continue
            }
            /* if there's a cheaper way of getting here already */
            if(openList.any { childPos == it.pos && currentNode.pathLength + 1 > it.pathLength }) {
                continue
            }
            /* add to open list */
            openList.add(GridNode(
                childPos,
                currentNode.pathLength + 1,
                manhattan(childPos, endPos),
                currentNode
            ))
        }
    }
    /* return null if the goal can't be reached */
    return null
}

/**
 * Generates a grid with obstacles.
 * @param size The size of the grid
 * @param obstacles List of obstacle coordinates
 * @return The generated grid
 */
fun initialiseGrid(size:Int, obstacles: List<Pair<Int, Int>>): List<MutableList<Char>> {
    val grid:List<MutableList<Char>> = List(size) { MutableList(size) {'.'} }
    for((row, col) in obstacles) {
        grid[row][col] = '#'
    }
    return grid
}

/**
 * Reads coordinates from a file.
 * @param filename The name of the input file
 * @return A list of coordinates
 */
fun question18(filename:String): MutableList<Pair<Int, Int>> {
    val coords:MutableList<Pair<Int,Int>> = mutableListOf()
    File(filename).forEachLine {
        val numbers = it.split(',').map { it.toInt() }
        /* flip the numbers to (row, col) form */
        coords.add(numbers[1] to numbers[0])
    }
    return coords
}

fun main() {
    val coords = question18("src/main/resources/Day18.txt")
    val grid = initialiseGrid(71, coords.take(1024))
    println(getShortestPath(grid, 0 to 0, grid.first().lastIndex to grid.first().lastIndex)!!.size - 1)
}
