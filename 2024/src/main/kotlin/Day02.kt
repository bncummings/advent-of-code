import java.io.File

fun formatListOfLines(filename: String): List<List<Int>> {
    val listOfLines: MutableList<List<Int>> = mutableListOf()
    var currentLine: List<Int>
    File(filename).forEachLine {
        currentLine = it.split(" ").map { num -> num.toInt() }
        listOfLines.add(currentLine)
    }
    return listOfLines
}

fun countSafeLevels(levelsList: List<List<Int>>, allowOne: Boolean): Int =
    levelsList.count { if (allowOne) levelsAreSafeBarOne(it) else levelsAreSafe(it) }

fun levelsAreSafeBarOne(levels: List<Int>):Boolean {
    return levelsAreSafe(levels) || levels.indices.map { i -> levelsAreSafeApartFromIndex(levels, i) }.any{ it }
}

fun levelsAreSafeApartFromIndex(levels: List<Int>, index: Int): Boolean {
    val newList: MutableList<Int> = levels.toMutableList()
    newList.removeAt(index)
    return levelsAreSafe(newList)
}

fun levelsAreSafe(levels: List<Int>):Boolean {
    val differenceFunction = getDifferenceFunction(levelsAreIncreasing(levels))
    return levels.zipWithNext { a, b -> differenceFunction(a, b) in 1..3 }.all { it }
}

fun getDifferenceFunction (increasing: Boolean): (Int,Int) -> Int =
    if(increasing) { x, y -> y - x} else { x, y -> x - y}

/**
 * constraint that each levels array has at least size 2
 */
fun levelsAreIncreasing(levels: List<Int>): Boolean {
    require(levels.size >= 2) { "Levels list must have at least two elements." }
    return levels[0] < levels[1]
}

fun main() {
    val data: List<List<Int>> = formatListOfLines("src/main/resources/Day02.txt")
    System.out.println(countSafeLevels(data, true))
    System.out.println(countSafeLevels(data, false))
}
