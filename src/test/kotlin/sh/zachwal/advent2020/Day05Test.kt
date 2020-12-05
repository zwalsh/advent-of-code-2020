package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day05Test {
    private val exampleBoardingPasses = listOf(
        "BFFFBBFRRR",
        "FFFBBBFRRR",
        "BBFFBBFRLL"
    )

    private val day05 = Day05()

    @Test
    fun `calc row from pass`() {
        assertEquals(70, day05.calcRow(exampleBoardingPasses[0]))
        assertEquals(14, day05.calcRow(exampleBoardingPasses[1]))
        assertEquals(102, day05.calcRow(exampleBoardingPasses[2]))
    }
    @Test
    fun `calc col from pass`() {
        assertEquals(7, day05.calcCol(exampleBoardingPasses[0]))
        assertEquals(7, day05.calcCol(exampleBoardingPasses[1]))
        assertEquals(4, day05.calcCol(exampleBoardingPasses[2]))
    }
    @Test
    fun `calculates seatId from pass`() {
        assertEquals(567, day05.seatId(exampleBoardingPasses[0]))
        assertEquals(119, day05.seatId(exampleBoardingPasses[1]))
        assertEquals(820, day05.seatId(exampleBoardingPasses[2]))
    }
}
