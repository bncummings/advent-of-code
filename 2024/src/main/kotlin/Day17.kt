import java.util.*
import kotlin.math.pow


/**
 * Each instruction also reads the 3-bit number after it as an input;
 * this is called its operand.
 */
enum class Inst {
    ADV, // ADV - Adjust register A by dividing it by 2^operand.
    BXL, // BXL - Performs bitwise XOR operation on register B with the given operand.
    BST, // BST - Sets register B to the operand value modulo 8.
    JNZ, // JNZ - Jumps to a specific instruction pointer if register A is non-zero.
    BXC, // BXC - Performs bitwise XOR operation on register B with register C.
    OUT, // OUT - Outputs the operand value modulo 8 to the output list.
    BDV, // BDV - Divides register A by 2^operand and stores the result in register B.
    CDV; // CDV - Divides register A by 2^operand and stores the result in register C.

    companion object {
        fun get(index:Int):Inst =
            entries[index]
    }
}

/**
 * Represents a Three-Bit Computer capable of running a program with 3-bit instructions.
 * @property registerA The first register (A).
 * @property registerB The second register (B).
 * @property registerC The third register (C).
 */
data class ThreeBitComputer(
    var registerA: Int,
    var registerB: Int,
    var registerC: Int,
) {
    val output: MutableList<Int> = ArrayList()
    private var instPointer = 0
    private val opCodeMap:EnumMap<Inst, (Int) -> Unit> = EnumMap(
        mapOf(
            Inst.ADV to { x ->
                registerA /= (2.0.pow(getComboOperand(x))).toInt()
                instPointer += 2
            },
            Inst.BXL to { x ->
                registerB = registerB xor x
                instPointer += 2
            },
            Inst.BST to { x ->
                registerB = getComboOperand(x) % 8
                instPointer += 2
            },
            Inst.JNZ to { x ->
                if (registerA != 0) {
                    instPointer = x
                } else {
                    instPointer += 2
                }
            },
            Inst.BXC to { _ ->
                registerB = registerB xor registerC
                instPointer += 2
            },
            Inst.OUT to { x ->
                output.add(getComboOperand(x) % 8)
                instPointer += 2
            },
            Inst.BDV to { x ->
                registerB = registerA / (2.0.pow(getComboOperand(x))).toInt()
                instPointer += 2
            },
            Inst.CDV to { x ->
                registerC = registerA / (2.0.pow(getComboOperand(x))).toInt()
                instPointer += 2
            }
        ))
    /**
     * Resolves an operand based on its value.
     * @param operand The operand to resolve.
     * @return The resolved operand value.
     * @throws IllegalArgumentException If the operand is not in the range [0..6].
     */
    private fun getComboOperand(operand:Int):Int =
        when (operand) {
            /* pass in literals if operand is in 0..3 */
            in 0..3 -> operand
            4 -> registerA
            5 -> registerB
            6 -> registerC
            else -> throw IllegalArgumentException("Invalid Operand")
        }

    /**
     * Runs the program.Initialise the instruction pointer as 0 and increment by 2 each instruction.
     * Halt when the instruction pointer is out of bounds.
     * @param program the program to run in the form of an even sized list of 3-bit numbers
     * @throws AssertionError if the program size is invalid
     */
    fun runProgram(program:List<Int>) {
        /* program must have an even number of instructions */
        assert(program.size % 2 == 0)
        /* clean the computer */
        instPointer = 0;
        output.clear()
        while(instPointer < program.size) {
            val instruction = opCodeMap[Inst.get(program[instPointer])]
            instruction!!.invoke(program[instPointer + 1])
        }
    }
}

fun main() {
    val program = listOf(2,4,1,1,7,5,1,5,0,3,4,4,5,5,3,0)
    val computer = ThreeBitComputer(23999685, 0, 0)
    computer.runProgram(program)
    println(computer.output)
}
