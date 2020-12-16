package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day15Test {
    private val day15 = Day15()
    private val starting = listOf(0, 3, 6)

    @Test
    fun `can generate next`() {
        assertEquals(listOf(0, 3, 6, 0, 3, 3, 1, 0, 4, 0), day15.memGameToSize(starting, 10))
    }
}
