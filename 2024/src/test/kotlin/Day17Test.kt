import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day17Test {
    @Test
    fun `runProgram sets the correct out put to the output file`() {
        var computer = ThreeBitComputer(0, 0, 9)
        computer.runProgram(listOf(2,6))
        assertEquals(
            1,
            computer.registerB
        )

        computer = ThreeBitComputer(10, 0, 0)
        computer.runProgram(listOf(5,0,5,1,5,4))
        assertEquals(
            listOf(0,1,2),
            computer.output
        )

        computer = ThreeBitComputer(729, 0, 0)
        computer.runProgram(listOf(0,1,5,4,3,0))
        assertEquals(
            listOf(4,6,3,5,6,3,5,2,1,0),
            computer.output
        )
    }
}