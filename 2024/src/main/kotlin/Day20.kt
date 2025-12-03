import java.util.Collections

fun countCheats(grid:List<List<Char>>, startPos: Pair<Int,Int>, endPos: Pair<Int,Int>): Int {
    var total = 0
    val pathSize = getShortestPath(grid, startPos, endPos)!!.size

    for(i in grid.indices) {
        for(j in grid.first().indices) {
            /* edge cases that won't make any effect */
            if(i == 0 || i == grid.lastIndex || j == 0 || j == grid.first().lastIndex) {
                continue
            }
            if(grid[i][j] == '#') {
                val cheatGrid = grid.map { it.toMutableList() }
                cheatGrid[i][j] = '.'
                val cheatPathSize = getShortestPath(cheatGrid, startPos, endPos)?.size
                if(pathSize - (cheatPathSize ?: 0) >= 100) {
                    total++
                }
            }
        }
    }

    return total
}


fun main() {
    val grid = getQuestionSixInput("src/main/resources/Day20.txt")
    var startPos = 0 to 0
    var endPos = 0 to 0

    for(i in grid.indices) {
        for(j in grid.first().indices) {
            when(grid[i][j]) {
                'S' -> startPos = i to j
                'E' -> endPos = i to j
                else -> continue
            }
        }
    }
    println(countCheats(grid, startPos, endPos))
}