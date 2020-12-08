package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day08Test {
    private val day08 = Day08()

    private val instructions = listOf(
        "nop +0",
        "acc +1",
        "jmp +4",
        "acc +3",
        "jmp -3",
        "acc -99",
        "acc +1",
        "jmp -4",
        "acc +6"
    ).map { day08.parseInstruction(it) }

    @Test
    fun `finds last value before infinite loop`() {
        assertEquals(Day08.ProgramResult(true, 5), day08.execute(instructions))
    }

    @Test
    fun `can parse number format`() {
        assertEquals(1, "+1".toInt())
        assertEquals(-1, "-1".toInt())
    }

    @Test
    fun `can fix corrupted program`() {
        assertEquals(8, day08.fixExecution(instructions))
    }
}
