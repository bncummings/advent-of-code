import java.io.File

typealias IntMatrix = List<List<Int>>

/**
 * Represents a Robot with a starting position and a movement vector.
 * @property startPos The starting position of the robot as a pair (row, column).
 * @property vector The movement vector of the robot as a pair (rowDelta, columnDelta).
 */
data class Robot(
    val startPos: Pair<Int, Int>,
    val vector: Pair<Int, Int>,
)
/**
 * Calculates the safety factor of the grid.
 * Calculated by dividing the grid into quadrants,summing the elements, and then multiplying the sums.
 * @param grid The 2D integer grid (IntMatrix) to calculate the safety factor for.
 * @return The safety factor as an integer.
 */
fun calculateSafetyFactor(grid:IntMatrix) =
    getQuadrants(grid).map { sumElementsInGrid(it) }.fold(1, Int::times)

fun sumElementsInGrid(grid:IntMatrix): Int =
    grid.sumOf { it.sum() }

/**
 * Divides a 2D grid into four quadrants.
 * @param grid The 2D integer grid (IntMatrix) to divide.
 * @return A list of four sub-grids representing the quadrants.
 */
fun getQuadrants(grid:IntMatrix): List<IntMatrix> {
    val length = grid.size
    val width = grid.first().size

    return listOf(
        grid.take(length / 2)
            .map { it.take(width / 2) },

        grid.take(length / 2)
            .map { it.drop((width / 2) + 1) },

        grid.drop((length / 2) + 1)
            .map { it.take(width / 2) },

        grid.drop((length / 2) + 1)
            .map { it.drop((width / 2) + 1) }
    )
}

/**
 * Simulates the grid after `n` seconds, with robots moving according to their vectors.
 * @param length The number of rows in the grid.
 * @param width The number of columns in the grid.
 * @param n The number of seconds for the simulation.
 * @param robots A list of robots with their starting positions and movement vectors.
 * @return A 2D mutable grid where each cell contains the count of robots at that position.
 */
fun gridAfterNSeconds(length:Int, width:Int, n:Int, robots:List<Robot>): List<MutableList<Int>> {
    fun robotAfterNSeconds(robot:Robot): Pair<Int, Int> {
        /* positive mod */
        val row = ((robot.startPos.first + (n * robot.vector.first)) % length + length) % length
        val col = ((robot.startPos.second + (n * robot.vector.second)) % width + width) % width

        return Pair(row, col)
    }

    val grid:List<MutableList<Int>> = List(length) { MutableList(width) { 0 } }
    for((i, j) in robots.map{robotAfterNSeconds(it)}) {
        grid[i][j]++
    }

    return grid
}

/**
 * Reads robot data from a file and constructs a list of robots.
 * @param filename The path to the file containing the robot data.
 * @return A mutable list of [Robot] objects.
 */
fun questionFourteen(filename:String): MutableList<Robot> {
    val robots: MutableList<Robot> = ArrayList()
    File(filename).forEachLine {
        val params = Regex("-?\\d+").findAll(it)
            .map { res -> res.value.toInt() }
            .toList()

        assert(params.size == 4)
        robots.add(Robot(
            Pair(params[1], params[0]),
            Pair(params[3], params[2])
        ))
    }
    return robots
}

fun main() {
    val robots = questionFourteen("src/main/resources/Day14.txt")
    val grid = gridAfterNSeconds(103, 101, 100, robots)
    println(calculateSafetyFactor(grid))
}