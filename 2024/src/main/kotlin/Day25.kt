import java.io.File

/**
 * Type alias for a mutable list of integers representing a lock or a key.
 */
typealias Lock = MutableList<Int>
typealias Key = MutableList<Int>

/**
 * Regular expression pattern to match a lock indicator.
 */
val LOCK_MATCHER = Regex("#####")

/**
 * Processes a file to extract locks and keys.
 * @param filename The name of the file to process.
 * @return A pair containing two lists: the first list represents locks, and the second list represents keys.
 */
fun getQuestion25(filename: String): Pair<MutableList<Lock>, MutableList<Key>> {
    val lockList = ArrayList<Lock>()
    val keyList = ArrayList<Key>()

    var currentMatrix = mutableListOf(0,0,0,0,0)
    var isLock = false
    var lineNumber = 1
    File(filename).forEachLine {
        when(lineNumber % 8) {
            0 -> if(isLock) lockList.add(currentMatrix) else keyList.add(currentMatrix)
            1 -> {
                /* first line of each block determines whether we have a lock or a key*/
                isLock = LOCK_MATCHER.matches(it)
                currentMatrix = mutableListOf(0,0,0,0,0)
            }
            /* we can always skip the 7th row assuming the input is formatted correctly */
            7 -> {}
            else -> it.forEachIndexed { index, c ->
                if(c == '#') currentMatrix[index]++
            }
        }
        lineNumber++
    }

    return Pair(lockList, keyList)
}

/**
 * Checks if a given key fits a lock based on the sum constraint.
 * @param key The key to check.
 * @param lock The lock to check against.
 * @return True if the key fits the lock, false otherwise.
 */
fun keyFitsLock(key: Key, lock: Lock): Boolean {
    for(i in lock.indices) {
        if(lock[i] + key[i] > 5) {
            return false
        }
    }
    return true
}

/**
 * Counts the total number of valid combinations between locks and keys.
 * @param lockList List of lock representations.
 * @param keyList List of key representations.
 * @return The total number of valid combinations.
 */
fun countCombinations(lockList: List<Lock>, keyList: List<Key>): Int {
    var total = 0
    for(lock in lockList) {
        for(key in keyList) {
            if(keyFitsLock(key,lock)) {
                total++
            }
        }
    }
    return total
}

fun main() {
    val (locks, keys) = getQuestion25("src/main/resources/Day25.txt")
    println(locks)
    println(keys)
    println(countCombinations(locks,keys))
}
