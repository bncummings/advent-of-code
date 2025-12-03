import kotlin.math.round

typealias Matrix = List<List<Double>>

data class Vector(val x:Double, val y:Double)

/**
 * @returns the inverted matrix or null if the matrix is singular
 */
fun invertMatrix(matrix: Matrix):Matrix? {
    val det =  determinant(matrix)
    if(det == 0.0) throw ArithmeticException("Matrix Not Invertible")

    return listOf(
        listOf( matrix[1][1] / det, -1 * matrix[0][1] / det),
        listOf(-1 * matrix[1][0] / det,  matrix[0][0] / det)
    ).map { it.map { n -> if(n == -0.0) 0.0  else n} }
}

/**
 * @returns the image of the argument vector rounded to 2dp
 */
fun leftMultiplyMatrix(matrix: Matrix, vector: Vector):Vector =
    Vector(
        round((matrix[0][0] * vector.x + matrix[0][1] * vector.y) * 100) / 100.0,
        round((matrix[1][0] * vector.x + matrix[1][1] * vector.y) * 100) / 100.0
    )

fun determinant(matrix:Matrix):Double =
    (matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1])
