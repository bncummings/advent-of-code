import java.io.File

const val PRUNE_CONSTANT = 16777216


/**
 * Performs a bitwise XOR operation between two long values.
 * @param secret The base value (secret number).
 * @param other The value to mix into the secret.
 * @return The result of performing the XOR operation between `secret` and `other`.
 */
fun mix(secret: Long, other: Long): Long =
    secret xor other

/**
 * Reduces the secret number to a bounded range by performing a modulo operation.
 * @param secret The value to prune.
 * @return The remainder when `secret` is divided by `PRUNE_CONSTANT`.
 */
fun prune(secret: Long): Long =
    secret % PRUNE_CONSTANT

/**
 * Evolves a secret number through a series of transformations.
 * @param secret The initial secret number.
 * @return The evolved secret number after applying the transformations.
 */
fun calculateSecret(secret: Long): Long {
    var result = secret
    result = prune(mix(result, result * 64))
    result = prune(mix(result, result / 32))
    result = prune(mix(result, result * 2048))
    return result
}

/**
 * Computes the nth evolution of a secret number.
 * @param secret The initial secret number.
 * @param n The number of iterations to evolve the secret.
 * @return The evolved secret number after `n` iterations.
 */
fun calculateNthSecret(secret: Long, n: Long): Long {
    var result = secret
    for (i in 1..n) {
        result = calculateSecret(result)
    }
    return result
}

/**
 * Computes the total sum of secrets from a file after evolving each number 2000 times.
 * @param filename The path to the input file containing secret numbers.
 * @return The total sum of the 2000th evolved secrets from the file.
 */
fun getQuestion22(filename: String): Long {
    var total = 0L
    File(filename).forEachLine {
        total += calculateNthSecret(it.toLong(), 2000)
    }
    return total
}

fun main() {
    println(getQuestion22("src/main/resources/Day22.txt"))
}
