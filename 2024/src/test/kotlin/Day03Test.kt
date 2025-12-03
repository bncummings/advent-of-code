import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.AssertionError
import kotlin.test.assertEquals

class Day03Test {
    @Test
    fun `regex matches single instance `() {
        val target = "mul(1,1)"
        assert(getMuls(target) == listOf(target))
    }

    @Test
    fun `regex matches many instances `() {
        assert(getMuls("mul(1,1) mul(2,2)")
                == listOf("mul(1,1)", "mul(2,2)")
        )
        assert(getMuls("mul(1,1) mul(2,2) mul(3,3)")
                == listOf("mul(1,1)", "mul(2,2)", "mul(3,3)")
        )
    }

    @Test
    fun `regex matches many instances with garbage in between`() {
        assert(getMuls("mul(1,1)3owty42h8qufp3ijo[mul(2,2)")
                == listOf("mul(1,1)", "mul(2,2)")
        )
        assert(getMuls("mul(1,1)mul(2,a)()sadfmul(2,2)mul(,3)mul(3,3)")
                == listOf("mul(1,1)", "mul(2,2)", "mul(3,3)")
        )
    }

    @Test
    fun `parseMul multiplies the entries in a mul`() {
        assert(parseMul("mul(2,2)") == 4)
        assert(parseMul("mul(2,0)") == 0)
        assert(parseMul("mul(9,5)") == 45)
    }

    @Test
    fun `parseMul rejects invalid muls`() {
        assertThrows<AssertionError> {
            parseMul("mul(9,-5)")
        }
        assertThrows<AssertionError> {
            parseMul("mul(  9 ,5)")
        }
        assertThrows<AssertionError> {
            parseMul("sum(1,1)")
        }
    }

    @Test
    fun `getSumOfMuls returns the sum of all valid muls in a string`() {
        val target = "mul(1,1)mul(2,a)()sadfmul(2,2)mul(,3)mul(3,3)"
        assert(getSumOfMuls(target) == (1 + 4 + 9))
    }

    @Test
    fun `regex matches dos and don'ts`() {
        assertEquals(
            listOf("do()"),
            getFunctions("do()")
        )
        assertEquals(
            listOf("don't()"),
            getFunctions("don't()")
        )
    }

    @Test
    fun `regex matches many instances of dos and don'ts`() {
        assertEquals(
            listOf("mul(1,1)", "mul(2,2)", "do()", "don't()"),
            getFunctions("mul(1,1) mul(2,2) do() don't()")
        )
    }

    @Test
    fun `regex matches many instances of dos and don'ts with garbage values`() {
        assertEquals(
            listOf("mul(1,1)", "mul(2,2)", "do()", "don't()"),
            getFunctions("mul(1,1)asdfadsfasdfasmul(2,2)dooo((((0 do()dont()do(0)don't((don't()")
        )
    }

    @Test
    fun `don'ts disable mul`() {
        assertEquals(0, parseDosAndDonts("don't() mul(2,2)"))
    }

    @Test
    fun `dos re-enable disable mul`() {
        assertEquals(4, parseDosAndDonts("don't() do() mul(2,2)"))
    }

    @Test
    fun `muls enabled by default`() {
        assertEquals(4, parseDosAndDonts("mul(2,2)"))
    }

    @Test
    fun `multiple dos and don'ts can be used to toggle`() {
        assertEquals(8, parseDosAndDonts("mul(2,2) don't() mul(2,2) do() mul(2,2)"))
    }

    @Test
    fun `consecutive dos and don'ts cancel out`() {
        assertEquals(12, parseDosAndDonts("mul(2,2) don't() do() mul(2,2)  mul(2,2)"))
    }

    @Test
    fun `consecutive dos have the same effect as a single do`() {
        assertEquals(12, parseDosAndDonts("mul(2,2) do() do() mul(2,2)  mul(2,2)"))
    }
}
