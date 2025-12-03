import java.io.File

fun formatListOfStrings(filename: String): List<String> {
    val listOfStrings: MutableList<String> = mutableListOf()
    File(filename).forEachLine {
        listOfStrings.add(it)
    }
    return listOfStrings
}

val xmasMatcher = Regex("(XMAS|SAMX)")
val part2Matcher = Regex("(MAS|SAM)")

fun wordSearchCount(stringMatrix: List<String>, matcher: Regex):Int {
    var total = 0
    stringMatrix.forEach {
        total += countPatternsInString(it, matcher)
    }
    transposeStringMatrix(stringMatrix).forEach {
        total += countPatternsInString(it, matcher)
    }
    getDiagonals(stringMatrix).forEach {
        total += countPatternsInString(it, matcher)
    }
    getDownwardDiagonals(stringMatrix).forEach {
        total += countPatternsInString(it, matcher)
    }
    return total
}

/**
 * assume all the strings are the same size
 * */
fun transposeStringMatrix(stringMatrix: List<String>): List<String> {
    val verticalList:MutableList<String> = MutableList(stringMatrix.first().length) {""}
    for (i in stringMatrix.first().indices) {
        for(j in stringMatrix.indices) {
            verticalList[i] = verticalList[i] + stringMatrix[j][i]
        }
    }
    return verticalList
}

fun getDiagonals(stringMatrix: List<String>): List<String> {
    val diagonalsList:MutableList<String> = mutableListOf()
    val maxX = stringMatrix.first().length - 1
    val maxY = stringMatrix.size - 1

    var currentDiagonal:String
    var i:Int; var j:Int; var k:Int = 0

    do {
        //set initial values for i and j
        if(k <= maxX) {
            //case where our diagonal touches the top row
            i = 0
            j = k
        } else {
            // diagonal does not touch the top row
            i = k - maxX
            j = maxX
        }

        currentDiagonal = ""
        do {
            currentDiagonal += stringMatrix[i][j]
            i++
            j--
        } while(i in stringMatrix.indices && j in stringMatrix.first().indices)
        diagonalsList.add(currentDiagonal)
        k++
    } while (k <= maxX + maxY)

    return diagonalsList
}

fun getDownwardDiagonals(stringMatrix: List<String>): List<String> {
    val diagonalsList:MutableList<String> = mutableListOf()
    val maxX = stringMatrix.first().length - 1
    val maxY = stringMatrix.size - 1

    var currentDiagonal:String
    var i:Int; var j:Int; var k:Int = 0

    do {
        //set initial values for i and j
        if(k <= maxX) {
            //case where our diagonal touches the top row
            i = 0
            j = maxX - k
        } else {
            // diagonal does not touch the top row
            i = k - maxX
            j = 0
        }

        currentDiagonal = ""
        do {
            currentDiagonal += stringMatrix[i][j]
            i++
            j++
        } while(i in stringMatrix.indices && j in stringMatrix.first().indices)
        diagonalsList.add(currentDiagonal)
        k++
    } while (k <= maxX + maxY)

    return diagonalsList
}

fun countPatternsInString(target:String, matcher:Regex) : Int =
    target.windowed(4, 1).count { matcher.matches(it) }

/**
 *  part two
 */

fun xmasOccurrences(stringMatrix: List<String>, matcher: Regex): Int {
    var total = 0
    for (i in stringMatrix.first().indices) {
        for (j in stringMatrix.indices) {
            if(stringMatrix[i][j] == 'A') {
                //check surrounding cross
                total += if(checkCross(stringMatrix, i, j, matcher)) 1 else 0
            }
        }
    }
    return total
}

fun checkCross(stringMatrix: List<String>, row:Int, col:Int, matcher: Regex):Boolean {
    //return if we are given an edge case
    if (
        row == 0 ||
        row == stringMatrix.size - 1 ||
        col == 0 ||
        col == stringMatrix.first().length - 1)
    {
        return false;
    }

    var downDiag = ""
    var j = col - 1
    for (i in row - 1..row + 1) {
        downDiag += stringMatrix[i][j]

        j++
    }

    var upDiag = ""
    j = col + 1
    for (i in row - 1..row + 1) {
        upDiag += stringMatrix[i][j]
        j--
    }

    return matcher.matches(upDiag) && matcher.matches(downDiag)
}

fun main() {
    val input = formatListOfStrings("src/main/resources/Day04.txt")
    System.out.println(xmasOccurrences(input, part2Matcher))
}
