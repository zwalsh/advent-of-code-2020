package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day10Test {
    val day10 = Day10()
    val adapters: List<Long> = listOf(
        28,
        33,
        18,
        42,
        31,
        14,
        46,
        20,
        48,
        47,
        24,
        23,
        49,
        45,
        19,
        38,
        39,
        11,
        1,
        32,
        25,
        35,
        8,
        17,
        7,
        9,
        4,
        2,
        34,
        10,
        3,
    )

    @Test
    fun `finds all 1 volt differences times all 3 volt differences`() {
        assertEquals(22 * 10, day10.joltageDifferences(adapters))
    }

    @Test
    fun `finds all arrangements`() {
        assertEquals(19208, day10.joltageArrangements(adapters))
    }
}
