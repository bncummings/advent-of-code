import java.io.File

/**
 * Collects the number matrix for question 10.
 * @param filename the path to the desired file
 */
fun questionTen(filename:String):List<List<Int>> {
    val input:MutableList<List<Int>> = mutableListOf()
    File(filename).forEachLine {
        input.add(it.toList().map { c -> c.digitToInt() })
    }
    return input
}

/**
 * Performs a depth first search on each 0 encountered.
 * If a 9 is reachable then we increment the number of trailheads.
 * @param grid the grid to be traversed
 * @return the number of valid trailheads
 */
fun sumTrailheads(grid: List<List<Int>>):Int {
    var total = 0
    val visitedSet:HashSet<Pair<Int, Int>> = HashSet()

    fun dfs(row:Int, col:Int) {
        visitedSet.add(Pair(row, col))
        if(grid[row][col] == 9) {
            total++
            return
        }
        for((newRow, newCol) in getSurrounding(row, col, grid)) {
            /* if they have a difference of one and we haven't visited */
            if((grid[newRow][newCol] - grid[row][col]) == 1  && !visitedSet.contains(newRow to newCol)) {
                dfs(newRow, newCol)
            }
        }
    }

    /* iterate through each square and start a dfs on each 0 encountered*/
    for(i in grid.indices) {
        for(j in grid.first().indices) {
            if(grid[i][j] == 0) {
                visitedSet.clear()
                dfs(i, j)
            }
        }
    }

    return total
}

/**
 * Helper function that returns a list of the adjacent squares to a given coordinate.
 * Assumes the given coordinate is already within bounds.
 * @param row the current row
 * @param col the current column
 * @param grid the grid to be traversed
 */
fun getSurrounding(row:Int, col:Int, grid:List<List<Int>>):List<Pair<Int,Int>> {
    val squares:MutableList<Pair<Int,Int>> = ArrayList()

    if(row > 0)                      squares.add(row - 1 to col)
    if(row < grid.lastIndex)         squares.add(row + 1 to col)
    if(col > 0)                      squares.add(row to col - 1)
    if(col < grid.first().lastIndex) squares.add(row to col + 1)

    return squares
}

/* part 2 */

/**
 * Performs a depth first search on each 0 encountered.
 * This version doesn't keep track of visited points but is free of cycles due to the requirement of increasing numbers.
 * If a 9 is reachable then we increment the number of trailheads.
 * @param grid the grid to be traversed
 * @return the number of valid trailheads
 */
fun sumTrailheadScores(grid: List<List<Int>>):Int {
    var total = 0

    fun dfs(row:Int, col:Int) {
        /* part 2: don't add 9s to the visited set in order to allow for many pa*/
        if(grid[row][col] == 9) {
            total++
            return
        }
        for((newRow, newCol) in getSurrounding(row, col, grid)) {
            /* if they have a difference of one and we haven't visited */
            if((grid[newRow][newCol] - grid[row][col]) == 1) {
                dfs(newRow, newCol)
            }
        }
    }

    /* iterate through each square and start a dfs on each 0 encountered*/
    for(i in grid.indices) {
        for(j in grid.first().indices) {
            if(grid[i][j] == 0) {
                dfs(i, j)
            }
        }
    }

    return total
}


fun main() {
    println(sumTrailheadScores(questionTen("src/main/resources/Day10.txt")))
}