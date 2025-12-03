// Area = number of nodes
// Perimeter = area * 4 - number of edges
// price = area * perimeter

data class Point(val x:Int, val y:Int)

/**
 * Calculates the total price of all regions in a grid.
 * - The area of a region is defined as the number of connected nodes (cells with the same character).
 * - The perimeter of a region is calculated as: (area * 4) - number of edges.
 * - The price of a region is defined as: area * perimeter.
 * @param grid A 2D list of characters representing the grid.
 * @return The total price of all regions in the grid.
 */

fun calculateTotalPrice(grid:List<List<Char>>): Int {
    val edges:MutableSet<Pair<Point, Point>> = HashSet()
    val visited:MutableSet<Point> = HashSet()
    var total = 0

    /**
     * Depth-First Search (DFS) to traverse connected regions.
     * Marks nodes as visited and tracks edges.
     * @param row The current row in the grid.
     * @param col The current column in the grid.
     */
    fun dfs(row:Int, col:Int) {
        visited.add(Point(row, col))

        for((newRow, newCol) in getSurrounding(row, col, grid)) {
            if(grid[newRow][newCol] == grid[row][col])
            {
                /* add the edge representing how we got to the next node
                but check duplicates first*/
                if(!edges.contains(Pair(Point(row, col), Point(newRow, newCol)))) {
                    edges.add(Pair(
                        Point(row, col),
                        Point(newRow, newCol)
                    ))
                }

                /* traverse to the next node if the adjacent square is part of the same plot
                and if we haven't visited yet */
                if(!visited.contains(Point(newRow, newCol))) {

                    dfs(newRow, newCol)
                }

            }
        }
    }

    for(i in grid.indices) {
        for(j in grid.first().indices) {
            if(!visited.contains(Point(i, j))) {
                val oldVisitedSize = visited.size
                val oldEdgesSize = edges.size
                dfs(i, j)
                val area = visited.size - oldVisitedSize
                val perimeter = (area * 4) - (edges.size - oldEdgesSize)
                total += area * perimeter
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
fun getSurrounding(row:Int, col:Int, grid:List<List<Char>>):List<Pair<Int,Int>> {
    val squares:MutableList<Pair<Int,Int>> = ArrayList()

    if(row > 0)                      squares.add(row - 1 to col)
    if(row < grid.lastIndex)         squares.add(row + 1 to col)
    if(col > 0)                      squares.add(row to col - 1)
    if(col < grid.first().lastIndex) squares.add(row to col + 1)

    return squares
}

/* Part 2: number of sides = number of corners */



fun main() {
    val grid = getQuestionSixInput("src/main/resources/Day12.txt")
    println(calculateTotalPrice(grid))
}