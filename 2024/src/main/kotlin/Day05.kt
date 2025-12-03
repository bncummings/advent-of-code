import java.io.File

class QuestionFiveInput(
    val rules: MutableList<Pair<Int, Int>>,
    val updates: MutableList<List<Int>>,
)

fun questionFive(filename: String):QuestionFiveInput {
    val input = QuestionFiveInput(ArrayList(), ArrayList())

    var current:List<Int>
    File(filename).forEachLine {
        if(Regex("\\d{2}\\|\\d{2}").matches(it)) {
            current = Regex("\\d{2}")
                .findAll(it)
                .toList()
                .map { match -> match.value.toInt() }

            assert(current.size == 2)
            input.rules.add(Pair(current[0], current[1]))
        } else if(Regex("((\\d{2},)|(\\d{2}))+").matches(it)) {
            input.updates.add(it.split(',').map { s -> s.toInt() })
        }
    }
    return input
}

fun sumMiddleNumbers(input: QuestionFiveInput) = getMiddleNumbersFromOrderedUpdates(input).sum()

fun inCorrectOrder(update: List<Int>, rules: List<Pair<Int,Int>>):Boolean {
    for(i in update.indices) {
        if(countPairsStartingWith(update[i], rules, update) != update.lastIndex - i)
            return false
    }

    return true
}

fun getMiddleNumbersFromOrderedUpdates(input: QuestionFiveInput): List<Int>  {
    val list:MutableList<List<Int>> = input.updates
    return list
        .filter { inCorrectOrder(it, input.rules) }
        .map { getMiddleNumber(it, input.rules) }
}

/**
 * we are certain that it will find one of these numbers
 * otherwise question would be unsolvable
 */
fun getMiddleNumber(update: List<Int>, rules: List<Pair<Int,Int>>): Int {
    //rounds down to the nearest integer
    val target = update.size / 2
    for(value in update) {
        if(countPairsStartingWith(value, rules, update) == target) {
            return value
        }
    }
    return 0;
}

/**
 * counts the number of pairs starting with @param value
 */
fun countPairsStartingWith(value: Int, pairs:List<Pair<Int,Int>>, update: List<Int>) =
    pairs.count {it.first == value && update.contains(it.second)}

/**
 * part 2
 */

fun getMiddleNumbersFromUnorderedUpdates(input: QuestionFiveInput): List<Int>  {
    val list:MutableList<List<Int>> = input.updates
    return list
        .filterNot { inCorrectOrder(it, input.rules) }
        .map { getMiddleNumber(it, input.rules) }
}

fun sumUnorderedMiddleNumbers(input: QuestionFiveInput) = getMiddleNumbersFromUnorderedUpdates(input).sum()

/**
 * updates size will always be odd and the correct update order is unique
 * hence, the middle element will be the only number to have floor(n/2) numbers succeeding it
 */
fun main() {
    questionFive("src/main/resources/Day05.txt")
    System.out.println(
        sumUnorderedMiddleNumbers(questionFive("src/main/resources/Day05.txt"))
    )
}