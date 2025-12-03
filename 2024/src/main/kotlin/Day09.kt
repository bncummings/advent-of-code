/**
 * takes input string of digits and produces the fragmented disk string
 * @param denseFormat dense summary of a disk as defined in the spec
 */
fun parseDenseFormat(denseFormat: String): List<String> {
    val result: MutableList<String> = mutableListOf()
    for (i in denseFormat.indices) {
        // every even index, denseFormat[i] represents a file
        val current = if (i % 2 == 0) (i / 2).toString() else "."
        for (j in 0..<denseFormat[i].digitToInt()) {
            result.add(current)
        }
    }
    return result
}

/**
 * takes input string of a fragmented disk returns un fragmented
 * @param disk the fragmented disk of the file
 */
fun defragmentDisk(disk: List<String>): List<String> {
    val defragDisk = disk.toMutableList()
    var (left, right) = updatePointers(0, disk.lastIndex, defragDisk)

    while (left < right) {
        // by now, the right pointer points to a digit and the left, to an empty location
        defragDisk[left] = defragDisk[right]
        defragDisk[right] = "."
        left++
        right--

        // go to the next . character
        val (newLeft, newRight) = updatePointers(left, right, defragDisk)
        left = newLeft
        right = newRight
    }
    return defragDisk
}

/**
 * helper function that updates the pointers
 * @param left goes to the next empty space from the left
 * @param right goes to the next file id from the right
 * @param disk the fragmented disk of the file
 */
fun updatePointers(left: Int, right: Int, disk: List<String>): Pair<Int, Int> {
    var newLeft = left
    var newRight = right

    while (disk[newRight] == ".") {
        newRight--
    }
    while (disk[newLeft] != ".") {
        newLeft++
    }
    return Pair(newLeft, newRight)
}

/**
 * multiplies each fileId with the new corresponding index in the disk
 * @param disk the fragmented disk of the file
 */
fun calculateCheckSum(disk: List<String>): Long {
    var total: Long = 0
    for (i in disk.indices) {
        if (disk[i] == ".") {
            continue
        }
        total += (i * disk[i].toInt()).toLong()
    }
    return total
}

/* Part 2 */

/**
 * goes through each contiguous group of file blocks from the right and reallocates them
 * @param disk the fragmented disk of the file
 */
fun optimiseDisk(disk: MutableList<String>) {
    var index: Int = disk.lastIndex
    var currentFileIndex: Int
    var fileSize: Int

    do {
        while(disk[index] == ".") {
            index--
        }
        // by now we've reached our next file
        currentFileIndex = index
        fileSize = 1

        // find the rest of this block
        // if the index before our current has the same fileId then add to the file size
        while(index > 0 && disk[index - 1] == disk[currentFileIndex]) {
            fileSize++
            index--
        }
        relocateFile(index, fileSize, disk)
        index--

    } while (index > 0)
}

/**
 * looks for any available space from the left of disk of given size and relocates that file if possible
 * @param index the index of the first file block in disk
 * @param fileSize the size of the file
 * @param disk the fragmented disk of the file
 */
fun relocateFile(index: Int, fileSize: Int, disk: MutableList<String>) {
    var left = 0
    while (left < index) {
        // if we reach an empty location and there's space
        if (disk[left] == "." &&
            (left..<left + fileSize).map { disk[it] }.all { it == "." }
        ) {
            //then we relocate our file here
            (left..<left + fileSize).forEach { disk[it] = disk[index] }
            (index..<index + fileSize).forEach { disk[it] = "." }
        }
        left++
    }
}

fun main() {
    val denseDisk = readToRawString("src/main/resources/Day09.txt")
    val disk = parseDenseFormat(denseDisk).toMutableList()
    optimiseDisk(disk)
    System.out.println(calculateCheckSum(disk))
}
