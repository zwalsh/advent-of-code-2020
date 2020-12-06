package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day06Test {
    private val input = listOf(
        listOf(
            "abc"
        ),
        listOf(
            "a",
            "b",
            "c"
        ),
        listOf(
            "ab",
            "ac"
        ),
        listOf(
            "a",
            "a",
            "a",
            "a"
        ),
        listOf(
            "b"
        )
    )
    private val day06 = Day06()

    @Test
    fun `counts unique answers across groups`() {
        assertEquals(11, day06.totalYesCount(input))
    }

    @Test
    fun `counts all shared answers across groups`() {
        assertEquals(6, day06.allYesCount(input))
    }

    @Test
    fun `counts shared within group`() {
        assertEquals(3, day06.sharedChars(input[0]))
        assertEquals(0, day06.sharedChars(input[1]))
        assertEquals(1, day06.sharedChars(input[2]))
        assertEquals(1, day06.sharedChars(input[3]))
    }
}
