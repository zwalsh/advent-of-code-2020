package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day14Test {
    private val day14 = Day14()
    private val exampleMask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"

    @Test
    fun `can apply mask to num`() {
        assertEquals(4, day14.applyBitmask("1XX", 0L))
        assertEquals(3, day14.applyBitmask("0XX", 7L))
        assertEquals(2, day14.applyBitmask("XXX", 2L))
        assertEquals(73, day14.applyBitmask(exampleMask, 11L))
        assertEquals(101, day14.applyBitmask(exampleMask, 101L))
        assertEquals(64, day14.applyBitmask(exampleMask, 0L))
    }
}
