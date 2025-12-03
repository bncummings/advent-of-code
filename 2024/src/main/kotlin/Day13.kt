import java.io.File
import kotlin.math.round

/* Say we are given input:
    Button A: X+94, Y+34
    Button B: X+22, Y+67
    Prize: X=8400, Y=5400

Then we are trying to find constants s and t that satisfy the equation:
    [ 94, 22 ] [ s ] = [ 8400 ]
    [ 22, 67 ] [ t ]   [ 5400 ]
Which we can solve by inverting the matrix on the left */

val A_MATCHER     = Regex("Button A: X\\+\\d+, Y\\+\\d+")
val B_MATCHER     = Regex("Button B: X\\+\\d+, Y\\+\\d+")
val PRIZE_MATCHER = Regex("Prize: X=\\d+, Y=\\d+")
val DIGIT_MATCHER = Regex("\\d+")
const val OFFSET = 10000000000000

/**
 * Class to store information about a claw machine.
 * Includes methods to calculate the unique number of button presses that will reach the prize if it exists.
 */
data class ClawMachine(
    var a:     Vector = Vector(0.0, 0.0),
    var b:     Vector = Vector(0.0, 0.0),
    var prize: Vector = Vector(0.0, 0.0)
) {
    /**
     * Calculates the solution to the system of equations for the number of times each button is pressed.
     * @return vector solution
     * @throws ArithmeticException if the matrix is singular, i.e. has determinant 0
     */
    fun calculateButtonPresses():Vector {
        val matrix = listOf(
            listOf(a.x, b.x),
            listOf(a.y, b.y)
        )
        try {
            return leftMultiplyMatrix(
                invertMatrix(matrix)!!,
                prize
            )
        } catch (e: ArithmeticException) {
            throw ArithmeticException("Cannot Invert Matrix")
        }
    }

    /**
     * Calculates the price of the buttons pressed if the vector solution contains positive integers only.
     * @return the price of the solution, 0 if there is no valid solution.
     */
    fun calculateTokens():Long {
        try {
            val btnVector = calculateButtonPresses()
            return if(
                btnVector.x > 0 &&
                btnVector.y > 0 &&
                btnVector.x % 1.0 == 0.0 &&
                btnVector.y % 1.0 == 0.0
            ) {
                println()
                3 * (btnVector.x).toLong() + round(btnVector.y).toLong()
            } else {
                0
            }

        } catch (e: ArithmeticException) {
            return 0
        }
    }
}

/**
 * Calculates the total price of the tokens requires to win every possible game
 * @param claws the list of claw objects to sum
 */
fun sumTokenPrices(claws:List<ClawMachine>) =
    claws.sumOf {
        it.calculateTokens()
    }

/**
 * Collects the grid and instruction set for question 13.
 * @param filename the path to the desired file
 * @return list of claw objects extracted form the file
 */
fun questionThirteen(filename: String): MutableList<ClawMachine> {
    val clawList:MutableList<ClawMachine> = mutableListOf()
    var currentClaw = ClawMachine();
    File(filename).forEachLine {
        val values:List<Double> = DIGIT_MATCHER.findAll(it)
            .toList().map { result -> result.value.toDouble() }
        when {
            A_MATCHER.matches(it) -> {
                currentClaw = ClawMachine()
                clawList.add(currentClaw)
                currentClaw.a = Vector(values[0] ,values[1])

            }
            B_MATCHER.matches(it) -> {
                currentClaw.b = Vector(values[0] ,values[1])
            }
            PRIZE_MATCHER.matches(it) -> {
                /* part 2: add offset to each prize vector */
                currentClaw.prize = Vector(OFFSET + values[0] ,OFFSET + values[1])
            }
        }
    }

    return clawList
}

fun main() {
    println(sumTokenPrices(questionThirteen("src/main/resources/Day13.txt")))
}
