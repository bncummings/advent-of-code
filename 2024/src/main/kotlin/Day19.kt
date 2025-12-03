import java.io.File

/**
 * Represents the input for Day19
 * @property availableTowels the available combinations that we can use
 * @property designs a list of potential designs composed of towel combinations
 */
data class Day19(
    var availableTowels: Set<String>,
    var designs: List<String>
) {
    companion object{
        /**
         * A wee factory method for creating the input object.
         * Wondering if this is more idiomatic for kotlin.
         * @param filename the filepath string
         * @throws NoSuchFileException if there's nae file
         */
        fun fromFile(filename:String): Day19 {
            val file = File(filename)
            return Day19(
                file.useLines { it.first() }.split(", ").toHashSet(),
                file.useLines { it.drop(2).toList() }
            )
        }
    }
}

/**
 * Dynamic Programming approach to filter possible designs
 * @param input the [Day19] input containing the language and combinations
 * @return the total number of valid words from the input
 */
fun getPossibleDesigns(input:Day19):List<String> {
    /* Dynamic Programming approach with memoization:
    a set of strings that we already know are valid */
    val memo:HashMap<String, Boolean> = HashMap(input.availableTowels.associateWith { true })
    fun isValidCombination(design:String):Boolean {
        if (design.isEmpty()) return true
        return memo.getOrPut(design) {
            /* try every substring */
            for(i in 1.. design.length) {
                if(memo.contains(design.take(i)) && memo[design.take(i)]!! && isValidCombination(design.drop(i))) {
                    return@getOrPut true
                }
            }
            return@getOrPut false
        }
    }

    return input.designs.filter { isValidCombination(it) }
}

/* part 2 */

/**
 * Dynamic Programming approach to filter possible designs
 * @param input the [Day19] input containing the language and combinations
 * @return the sum of the total number of ways we can make each word
 */
fun getTotalSolutions(input:Day19):List<Long> {
    /* Each string from the available towels has 1 'way we can make it' */
    val memo: HashMap<String, Long> = HashMap()

    fun getNumSolutions(design:String):Long {
        if (design.isEmpty()) return 1
        return memo.getOrPut(design) {
            /* try every substring */
            var numSolutions = 0L
            for(i in 1.. design.length) {
                if(input.availableTowels.contains(design.take(i))) {
                    numSolutions += getNumSolutions(design.drop(i))
                }
            }
            return@getOrPut numSolutions
        }
    }

    return input.designs.map{ getNumSolutions(it) }
}

fun main() {
    val input = Day19.fromFile("src/main/resources/Day19.txt")
    println(input)
    println(getTotalSolutions(input).sum())
}