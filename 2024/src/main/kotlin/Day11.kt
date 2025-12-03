/**
 * Function that performs n blinks on a given list of numbers
 * @param seed the starting list of numbers
 * @param n the number of iterations
 * @return the final output list
 */
fun nBlinks(seed: List<Long>, n:Int):List<Long> {
    var list = seed
    var prevSize:Int
    for(i in 0..< n) {
        prevSize = list.size
        list = blink(list)
        println("${list.size - prevSize} new entries")
    }
    return list
}

/**
 * Function that performs a single blink and returns the output
 * @param list the starting list of numbers
 * @return the output list
 */
fun blink(list:List<Long>):List<Long> =
    list.flatMap { n ->
        when {
            n == 0L -> listOf(1)
            n.toString().length % 2 == 0 -> {
                val literal = n.toString()
                val size = literal.length

                listOf(
                    literal.take(size / 2).toLong(),
                    literal.drop(size / 2).toLong()
                )
            }
            else -> listOf(2024 * n)
        }
    }


fun main () {
    val seed:List<Long> = listOf(572556, 22, 0, 528, 4679021, 1, 10725, 2790)
    println(nBlinks(seed, 25).size)
    println(nBlinks(seed, 75).size)
}