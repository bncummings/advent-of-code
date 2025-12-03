import java.io.File

val GRID_MATCHER = Regex("#.+#")
val INST_MATCHER = Regex("[\\^v><]+")

/**
 * Represents the grid and instruction set of the robot.
 */
class QuestionFifteenInput(
    val grid: MutableList<MutableList<Char>>,
    val instructions: MutableList<Char>,
)

/**
 * Collects the grid and instruction set for question 15.
 * @param filename the path to the desired file
 */
fun questionFifteen(filename: String):QuestionFifteenInput {
    val input = QuestionFifteenInput(ArrayList(), ArrayList())
    File(filename).forEachLine {
        if(GRID_MATCHER.matches(it)) {
            input.grid.add( it.toMutableList())
        } else if(INST_MATCHER.matches(it)) {
            input.instructions.addAll(it.toList())
        }
    }
    return input
}

/**
 * Calculates the sum of all the boxes coordinates of the sorted unit.
 * using calculateCoordinate()
 * @param grid the current state of the grid
 */
fun sumBoxCoordinates(grid: List<List<Char>>):Int {
    var total = 0
    fun calculateCoordinate(row:Int, col:Int):Int = (100 * row) + col

    for(i in grid.indices) {
        for(j in grid.first().indices) {
            if(grid[i][j] == 'O') {
                total += calculateCoordinate(i, j)
            }
        }
    }
    return total
}

/**
 * Finds and returns the coordinates of the robot.
 * To be used at the start of the simulation.
 * @param grid the current state of the grid
 * @return coordinates of the robot, null if not found
 */
fun getRobotCoordinates(grid: List<List<Char>>):Pair<Int, Int>? {
    for(i in grid.indices) {
        for(j in grid.first().indices) {
            if(grid[i][j] == '@') {
                return Pair(i, j)
            }
        }
    }
    return null
}

/**
 * Moves a block/robot by recursively calling itself on the desired location
 * @param row the current row of the object
 * @param col the current row of the object
 * @param grid the current state of the grid
 * @param direction the direction of travel using the Direction enum from day 6
 * @return true if it's happened successfully
 */
fun moveCharacter(row:Int, col:Int, direction: Direction, grid: List<MutableList<Char>>):Boolean {
    /* base case: we return true if the there is an empty space and false if there's a wall
    in each case, nothing moves */
    if(grid[row][col] == '.') return true
    if(grid[row][col] == '#') return false

    /* we don't need t consider index out of bounds errors as all the edge cases contain '#' characters*/
    val (newRow, newCol) = moveOneSquare(row, col, direction)

    /* we update the grid and return true if we can call moveCharacter() on our new row and col.
    This will only update if the base case returns true, i.e. there is an empty space available */
    if(moveCharacter(newRow, newCol, direction, grid)) {
        grid[newRow][newCol] = grid[row][col]
        grid[row][col] = '.'
        return true
    }
    return false
}

/**
 * Moves the robot according to given instructions, updating the grid for each one
 * @param startRow the current row of the object
 * @param startCol the current row of the object
 * @param input the current state of the grid
 */
fun simulatePacking(startRow:Int, startCol:Int, input: QuestionFifteenInput) {
    var row = startRow
    var col = startCol
    val grid = input.grid
    val instructions = input.instructions

    for(char in instructions) {
        val direction = when(char) {
            '^'   -> Direction.UP
            '>'   -> Direction.RIGHT
            'v'   -> Direction.DOWN
            '<'   -> Direction.LEFT
            else -> null
        }

        /* move our robot if we can */
        if(moveCharacter(row, col, direction!!, grid)) {
            val (newRow, newCol) = moveOneSquare(row, col, direction)
            row = newRow
            col = newCol
        }
    }
}

/**
 * Helper function to move the robot by one square depending on the direction.
 * Doesn't handle index out of bounds errors.
 * @param row the current row of the object
 * @param col the current row of the object
 * @param direction the direction of travel using the Direction enum from day 6
 */
fun moveOneSquare(row:Int, col:Int, direction: Direction):Pair<Int, Int> =
    when (direction) {
        Direction.UP    -> row - 1 to col
        Direction.RIGHT -> row     to col + 1
        Direction.DOWN  -> row + 1 to col
        Direction.LEFT  -> row     to col - 1
    }

/* Part 2 */

fun widenGrid(grid:List<MutableList<Char>>):List<MutableList<Char>> =
    grid.map {it.flatMap { c ->
        when(c) {
            '#' -> "##".toList()
            '@' -> "@.".toList()
            'O' -> "[]".toList()
            else -> "..".toList()
        }
    }.toMutableList()
    }


fun main() {
    val input = questionFifteen("src/main/resources/Day15.txt")
    val startingPos = getRobotCoordinates(input.grid)!!

    simulatePacking(
        startingPos.first,
        startingPos.second,
        input)

    println(sumBoxCoordinates(input.grid))
}