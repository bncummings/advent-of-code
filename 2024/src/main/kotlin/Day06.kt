import java.io.File

enum class Direction {
    UP, RIGHT, DOWN, LEFT
}

data class Guard(var position: Pair<Int, Int>, var direction: Direction) {
    val visitedSet : HashSet<Pair<Int, Int>> = hashSetOf(position)

    fun move(row:Int, col:Int) {
        //collect current position
        position = Pair(row, col)
        System.out.println(position)
        visitedSet.add(position)
    }

    fun turn90Degrees() {
        direction = Direction.entries[(direction.ordinal + 1) % 4]
    }
}

/**
 * give a list of chars and a start index we need to iterate through them
 * and add the coords to coordSet of each '.' character
 *
 * @return true if it terminates, false if it times out
 */
fun patrol(grid: List<List<Char>>, guard: Guard):Boolean {
    var row: Int = guard.position.first
    var col: Int = guard.position.second
    var iterations: Int = 0
    //loop should terminate when guard reaches the edge
    while(!onEdge(grid, guard)) {
        //collect this square
        guard.move(row, col)

        //calculate next move
        val (newRow, newCol) = when (guard.direction) {
            Direction.UP    -> row - 1 to col
            Direction.RIGHT -> row     to col + 1
            Direction.DOWN  -> row + 1 to col
            Direction.LEFT  -> row     to col - 1
        }

        if(!onEdge(grid, guard) && grid[newRow][newCol] == '#') {
            guard.turn90Degrees()
        } else {
            row = newRow
            col = newCol
        }

        // catch infinite loops
        iterations++
        if(iterations >= 10000) {
            return false
        }
    }

    return true
}

fun onEdge(grid: List<List<Char>>, guard: Guard):Boolean =
    (guard.position.first == grid.lastIndex && guard.direction == Direction.DOWN) ||    //  facing down and on the bottom edge
    (guard.position.first == 0 && guard.direction == Direction.UP) ||                   //  facing up and on the top edge
    (guard.position.second == grid.first().lastIndex && guard.direction == Direction.RIGHT) ||  //  facing right and on the right edge
    (guard.position.second == 0 && guard.direction == Direction.LEFT)                   //  facing left and on the left edge

fun countInfiniteLoops(grid: List<List<Char>>):Int {
    val startPosition = findGuard(grid)!!
    var totalLoops = 0

    for(i in grid.indices) {
        for(j in grid.first().indices) {
            //don't try and replace the guard with an obstacle
            if(Pair(i, j) == startPosition) {
                continue;
            }
            //add new obstacle
            val newGrid = grid.map { it.toMutableList() }
            newGrid[i][j] = '#'

            totalLoops +=
                if(!patrol(newGrid, Guard(startPosition, Direction.UP))) 1 else 0
        }
    }

    return totalLoops

}

fun main() {
    val grid = getQuestionSixInput("src/main/resources/Day06.txt")
    val guard = Guard(findGuard(grid)!!, Direction.UP)
//
    System.out.println(countInfiniteLoops(grid))
}

/**
 * returns a list of strings/ matrix of characters
 */
fun getQuestionSixInput(filename: String): List<List<Char>> {
    val listOfStrings: MutableList<List<Char>> = mutableListOf()
    File(filename).forEachLine {
        listOfStrings.add(it.toList())
    }
    return listOfStrings
}

fun findGuard(grid:List<List<Char>>):Pair<Int, Int>? {
    for(i in grid.indices) {
        for(j in grid.first().indices) {
            if("^>v<".contains(grid[i][j])) {
                return Pair<Int, Int>(i, j)
            }
        }
    }
    return null
}
