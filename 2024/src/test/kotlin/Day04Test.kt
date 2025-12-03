import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {
    @Test
    fun `matches XMAS`() {
        assertEquals(
            1,
            countPatternsInString("XMAS", xmasMatcher)
        )
    }

    @Test
    fun `matches SAMX`() {
        assertEquals(
            1,
            countPatternsInString("SAMX", xmasMatcher)
        )
    }

    @Test
    fun `matches multiple`() {
        assertEquals(
            3,
            countPatternsInString("SAMXXMASSAMX", xmasMatcher)
        )
    }

    @Test
    fun `matches multiple with overlapping letters`() {
        assertEquals(
            3,
            countPatternsInString("SAMXMASAMX", xmasMatcher)
        )
    }

    @Test
    fun `can convert from horizontal to vertical strings`() {
        val wordSearch = listOf(
            "ab",
            "cd"
        )
        val wordSearchTranspose = listOf(
            "ac",
            "bd"
        )

        assertEquals(wordSearchTranspose, transposeStringMatrix(wordSearch))
    }

    @Test
    fun `can convert from horizontal to vertical strings with irregular length`() {
        val wordSearch = listOf(
            "abc",
            "def"
        )
        val wordSearchTranspose = listOf(
            "ad",
            "be",
            "cf"
        )

        assertEquals(wordSearchTranspose, transposeStringMatrix(wordSearch))
    }

    @Test
    fun `getDiagonals returns a list of diagonal strings`() {
        var wordSearch = listOf(
            "ab",
            "de"
        )
        var diagonalList = listOf(
            "a",
            "bd",
            "e"
        )
        assertEquals(diagonalList, getDiagonals(wordSearch))

        wordSearch = listOf(
            "abc",
            "def",
            "ghi"
        )
        diagonalList = listOf(
            "a",
            "bd",
            "ceg",
            "fh",
            "i"
        )
        assertEquals(diagonalList, getDiagonals(wordSearch))
    }

    @Test
    fun `getDownwardDiagonals returns a list of diagonal strings`() {
        var wordSearch = listOf(
            "ab",
            "de"
        )
        var diagonalList = listOf(
            "b",
            "ae",
            "d"
        )
        assertEquals(diagonalList, getDownwardDiagonals(wordSearch))

        wordSearch = listOf(
            "abc",
            "def",
            "ghi"
        )
        diagonalList = listOf(
            "c",
            "bf",
            "aei",
            "dh",
            "g"
        )
        assertEquals(diagonalList, getDownwardDiagonals(wordSearch))
    }

    @Test
    fun `wordSearch returns the number of occurances`() {
        val wordSearch = listOf(
          "MMMSXXMASM",
          "MSAMXMSMSA",
          "AMXSXMAAMM",
          "MSAMASMSMX",
          "XMASAMXAMM",
          "XXAMMXXAMA",
          "SMSMSASXSS",
          "SAXAMASAAA",
          "MAMMMXMMMM",
          "MXMXAXMASX",
        )
        assertEquals(18, wordSearchCount(wordSearch, xmasMatcher))
    }

    @Test
    fun `xmasOccurrences returns the number of x-mas matches`() {
        val wordSearch = listOf(
            "MMMSXXMASM",
            "MSAMXMSMSA",
            "AMXSXMAAMM",
            "MSAMASMSMX",
            "XMASAMXAMM",
            "XXAMMXXAMA",
            "SMSMSASXSS",
            "SAXAMASAAA",
            "MAMMMXMMMM",
            "MXMXAXMASX",
        )
        assertEquals(9, xmasOccurrences(wordSearch, part2Matcher))
    }
}
