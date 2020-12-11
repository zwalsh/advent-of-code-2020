package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day11Test {

    private val day11 = Day11()
    private val grid = day11.parse(
        listOf(
            "L.LL.LL.LL",
            "LLLLLLL.LL",
            "L.L.L..L..",
            "LLLL.LL.LL",
            "L.LL.LL.LL",
            "L.LLLLL.LL",
            "..L.L.....",
            "LLLLLLLLLL",
            "L.LLLLLL.L",
            "L.LLLLL.LL"
        )
    )

    @Test
    fun `counts adjacent occupied`() {
        assertEquals(0, day11.countAdjacentOccupied(0, 0, grid))
        assertEquals(
            8,
            day11.countAdjacentOccupied(
                1, 1,
                day11.parse(
                    listOf(
                        "###",
                        "#L#",
                        "###"
                    )
                )
            )
        )
    }

    @Test
    fun `can find stable grid`() {
        assertEquals(37, day11.occupiedCount(grid))
    }

    @Test
    fun `counts visible occupied`() {
        assertEquals(0, day11.countVisibleOccupied(0, 0, grid))
        val input = day11.parse(
            listOf(
                "#...#",
                ".....",
                "..L..",
                ".....",
                "#...#"
            )
        )
        assertEquals(
            4, day11.countVisibleOccupied(
                2, 2,
                input
            )
        )
    }

    @Test
    fun `finds stable with new rules`() {
        assertEquals(26, day11.occupiedCount(grid, day11::countVisibleOccupied, 5))
    }
}
