package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day01KtTest {

    private val exampleInput = listOf(
        1721,
        979,
        366,
        299,
        675,
        1456,
    )

    private val day01 = Day01()

    @Test
    fun `produces product of expenses summing to 2020`() {
        assertEquals(514579, day01.expenseReportTwoExpenses(exampleInput))
    }

    @Test
    fun `produces product of 3 expenses summing to 2020`() {
        assertEquals(241861950, day01.expenseReportThreeExpenses(exampleInput))
    }
}
