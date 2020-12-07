package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day07Test {
    private val bagRules = listOf(
        "light red bags contain 1 bright white bag, 2 muted yellow bags.",
        "dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
        "bright white bags contain 1 shiny gold bag.",
        "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.",
        "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.",
        "dark olive bags contain 3 faded blue bags, 4 dotted black bags.",
        "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.",
        "faded blue bags contain no other bags.",
        "dotted black bags contain no other bags.",
    )
    private val day07 = Day07()

    @Test
    fun `calculates number of root bags for a bag`() {
        assertEquals(4, day07.roots(bagRules, "shiny gold"))
    }

    @Test
    fun `can parse a bag rule`() {
        assertEquals(
            Day07.BagRule(
                "light red",
                mapOf("bright white" to 1, "muted yellow" to 2)
            ),
            day07.parseBagRule(bagRules[0])
        )
    }

    @Test
    fun `calculates total bag capacity of nested bags`() {
        assertEquals(32, day07.totalCapacity(bagRules, "shiny gold"))
    }
}
