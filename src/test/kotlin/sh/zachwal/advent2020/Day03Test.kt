package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day03Test {
    private val input = listOf(
        "..##.......",
        "#...#...#..",
        ".#....#..#.",
        "..#.#...#.#",
        ".#...##..#.",
        "..#.##.....",
        ".#.#.#....#",
        ".#........#",
        "#.##...#...",
        "#...##....#",
        ".#..#...#.#",
    )

    @Test
    fun `counts all trees with 3-1 slope`() {
        assertEquals(7, Day03().countTrees(input))
    }

    @Test
    fun `can do vertical slope`() {
        assertEquals(2, Day03().countTrees(input, 1, 2))
    }

    @Test
    fun `can find product of multiple slopes`() {
        assertEquals(336, Day03().countMultipleSlopes(input))
    }
}
