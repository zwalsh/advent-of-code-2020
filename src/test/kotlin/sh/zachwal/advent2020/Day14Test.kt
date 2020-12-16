package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day14Test {
    private val day14 = Day14()
    private val exampleMask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"

    private val exampleProgram = listOf(
        "mask = 000000000000000000000000000000X1001X",
        "mem[42] = 100",
        "mask = 00000000000000000000000000000000X0XX",
        "mem[26] = 1",
    )

    @Test
    fun `can apply mask to num`() {
        assertEquals(4, day14.applyBitmask("1XX", 0L))
        assertEquals(3, day14.applyBitmask("0XX", 7L))
        assertEquals(2, day14.applyBitmask("XXX", 2L))
        assertEquals(73, day14.applyBitmask(exampleMask, 11L))
        assertEquals(101, day14.applyBitmask(exampleMask, 101L))
        assertEquals(64, day14.applyBitmask(exampleMask, 0L))
    }

    @Test
    fun `can calculate all memory addresses from mask`() {
        assertEquals(
            listOf(
                26L,
                27L,
                58L,
                59L
            ),
            day14.maskedAddresses(42, "000000000000000000000000000000X1001X")
        )
    }

    @Test
    fun `can calculate memory space with memory address masking`() {
        assertEquals(208, day14.memoryWithAddressMasking(exampleProgram).values.sum())
    }
}
