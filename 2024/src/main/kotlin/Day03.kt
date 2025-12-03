import java.io.File

fun readToRawString(filename: String): String =
    File(filename).readText()

val mulMatcher = Regex("mul\\(\\d+,\\d+\\)")
val functionMatcher = Regex("(mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\))")
val doMatcher = Regex("do\\(\\)")
val dontMatcher = Regex("don't\\(\\)")

/**
 *  part one
 */
fun getSumOfMuls(target:String) =
    getMuls(target)
        .map { parseMul(it) }
        .fold(0, Int::plus)

fun getMuls(target:String):List<String> =
    mulMatcher.findAll(target).map { it.value }.toList()

fun parseMul(mulString:String):Int {
    assert(mulString.matches(mulMatcher))
    return "\\d+".toRegex().findAll(mulString).map {it.value.toInt()}.fold(1, Int::times)
}

/**
 *  part two
 */
fun getFunctions(target:String):List<String> =
    functionMatcher.findAll(target).map { it.value }.toList()

fun parseDosAndDonts(target:String): Int {
    var total = 0
    var mulsEnabled = true
    getFunctions(target).forEach {
        when {
            doMatcher.matches(it) -> mulsEnabled = true
            dontMatcher.matches(it) -> mulsEnabled = false
            mulMatcher.matches(it) && mulsEnabled -> total += parseMul(it)
        }
    }
    return total
}

fun main() {
    val corruptedMemory: String = readToRawString("src/main/resources/Day03.txt")
    System.out.println(getSumOfMuls(corruptedMemory))
    System.out.println(parseDosAndDonts(corruptedMemory))
}