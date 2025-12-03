import java.io.File

/**
 * Data class representing a test case with a target value and a list of integer values.
 * @property target The target value that needs to be achieved.
 * @property values The list of integer values to be used for calculations.
 */
data class TestData(val target: Long, val values: List<Int>)

/**
 * Concatenates a Long and an Int into a single Long.
 * @receiver The original Long value.
 * @param other The integer to concatenate with the receiver Long.
 * @return A new Long value formed by concatenating the receiver and the parameter as strings.
 */
fun Long.concat(other:Int):Long =
    (this.toString() + other.toString()).toLong()

/**
 * Checks whether a given [TestData] object is valid.
 * @param test The [TestData] object containing the target value and the list of integers.
 * @return `true` if a sequence of operations can produce the target, `false` otherwise.
 */
fun isValid(test: TestData):Boolean {

    fun isListValid(acc: Long, values: List<Int>):Boolean {
        /* Base Case: if we reach the end of the list, return true iff the acc has reached the target*/
        if(values.isEmpty()) {
            return acc == test.target
        }
        /* prune any branch that's already gone beyond the target */
        if(acc > test.target) {
            return false
        }
        /* dfs for a solution permutation */
        /* Part 2: include the concat operator */
        return isListValid(acc + values.first(), values.drop(1)) ||
               isListValid(acc * values.first(), values.drop(1)) ||
                isListValid(acc.concat(values.first()), values.drop(1))
    }

    return isListValid(test.values.first().toLong(), test.values.drop(1))
}

/**
 * Counts the total of target values for all valid [TestData] objects in the provided list.
 * @param tests A list of [TestData] objects to validate.
 * @return The sum of the target values of all valid test cases.
 */
fun countValidTests(tests: List<TestData>):Long {
    var total = 0L
    for(test in tests) {
        if(test.values.size == 1) {
            println("singleton found")
        }
        if(isValid(test)) {
            total += test.target
            //println(total)
        }
    }
    return total
}

fun main() {
    println(countValidTests(questionSeven("src/main/resources/Day07.txt")))
}

/**
 * Reads test data from a file and parses it into a list of [TestData] objects.
 * @param filename The path to the input file containing test data.
 * @return A mutable list of [TestData] objects parsed from the file.
 */
fun questionSeven(filename:String): MutableList<TestData> {
    val testDataList:MutableList<TestData> = mutableListOf()
    File(filename).forEachLine {
        val line = it.split(":")
        testDataList.add(TestData(
            line[0].toLong(),
            line[1].split(" ")
                .filterNot(String::isEmpty)
                .map { s -> s.toInt() }
        )
        )
    }
    return testDataList
}