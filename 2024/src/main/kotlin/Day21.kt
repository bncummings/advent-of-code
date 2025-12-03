/**
 * Maps keypad characters to their corresponding positions on the numeric keypad.
 */
val numKeypadMap: HashMap<Char, Pair<Int, Int>> = hashMapOf(
    'A' to Pair(3,2),
    '0' to Pair(3,1),
    '1' to Pair(2,0),
    '2' to Pair(2,1),
    '3' to Pair(2,2),
    '4' to Pair(1,0),
    '5' to Pair(1,1),
    '6' to Pair(1,2),
    '7' to Pair(0,0),
    '8' to Pair(0,1),
    '9' to Pair(0,2),
)

/**
 * Maps directional characters to their corresponding movements on the keypad.
 */
val dirKeypadMap: HashMap<Char, Pair<Int, Int>> = hashMapOf(
    'A' to Pair(0,2),
    '^' to Pair(0,1),
    '<' to Pair(1,0),
    'v' to Pair(1,1),
    '>' to Pair(1,2),
)

/**
 * Calculates the complexity of a given code sequence.
 * @param code The input code sequence to analyze.
 * @return An integer representing the complexity of the code sequence.
 */
fun calculateComplexity(code: String): Int {
    var sequence = getSequence(code, numKeypadMap)
    sequence = getSequence(getSequence(sequence, dirKeypadMap), dirKeypadMap)
    return code.take(3).toInt() * sequence.length
}

/**
 * Extracts a sequence of keypad characters from a given string.
 * @param buttons The input string containing keypad characters.
 * @param map The mapping of characters to their positions on the keypad.
 * @return A string representing the extracted sequence of keypad characters.
 */
fun getSequence(buttons: String, map: Map<Char, Pair<Int, Int>>): String =
    getMinSequence(
        map['A']!!, buttons.map { map[it]!! }
    )

/**
 * Generates the minimum sequence of instructions to press buttons at given coordinates.
 * @param start The starting position as a pair of integers (row, column).
 * @param buttons A list of target button positions as pairs of integers (row, column).
 * @return A string representing the minimum sequence of instructions.
 */
fun getMinSequence(start: Pair<Int, Int>, buttons: List<Pair<Int, Int>>): String {
    var current: Pair<Int, Int> = start
    var result = ""
    for(button in buttons) {
        val (dRow, dCol) = button - current
        val updo = if(dRow >= 0) {
            "v".repeat(dRow)
        } else {
            "^".repeat(-dRow)
        }
        val leri = if(dCol >= 0) {
            ">".repeat(dCol)
        } else {
            "<".repeat(-dCol)
        }

        result += if(dCol < 0) {
            leri + updo + "A"
        } else {
            updo + leri + "A"
        }

        current = button
    }
    return result
}

fun main() {
    println(
        listOf(
            "836A",
            "540A",
            "965A",
            "480A",
            "789A"
        ).sumOf { calculateComplexity(it) }
    )
}
