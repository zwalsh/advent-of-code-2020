package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day09Test {
    private val day09 = Day09()
    private val exampleStream = listOf(
        35,
        20,
        15,
        25,
        47,
        40,
        62,
        55,
        65,
        95,
        102,
        117,
        150,
        182,
        127,
        219,
        299,
        277,
        309,
        576,
    ).map { it.toLong() }

    @Test
    fun `can identify first after preamble that does not sum to previous pair`() {
        assertEquals(127, day09.firstNotMatching(exampleStream, 5))
    }

    @Test
    fun `can find contiguous set summing to target`() {
        assertEquals(62, day09.contiguousSumOneActiveSet(exampleStream, 5))
    }
}
