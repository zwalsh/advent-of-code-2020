package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day16Test {
    private val day16 = Day16()

    private val exampleRules = listOf(
        "class: 1-3 or 5-7",
        "row: 6-11 or 33-44",
        "seat: 13-40 or 45-50"
    ).map { day16.parseRule(it) }
    private val yourTicket = listOf(
        7, 1, 14
    )
    private val nearbyTickets = listOf(
        listOf(7, 3, 47),
        listOf(40, 4, 50),
        listOf(55, 2, 20),
        listOf(38, 6, 12),
    )

    private val validRules = listOf(
        "class: 0-1 or 4-19",
        "row: 0-5 or 8-19",
        "seat: 0-13 or 16-19",
    ).map { day16.parseRule(it) }
    private val validTickets = listOf(
        listOf(3, 9, 18),
        listOf(15, 1, 5),
        listOf(5, 14, 9),
    )

    @Test
    fun `can parse a rule from a string`() {
        assertEquals(Day16.FieldRule("hi", setOf(1, 2, 3)), day16.parseRule("hi: 1-3"))
        assertEquals(Day16.FieldRule("hi", setOf(1, 2, 3, 6, 7, 8)), day16.parseRule("hi: 1-3 or 6-8"))
    }

    @Test
    fun `can find all completely invalid fields on a ticket`() {
        assertEquals(emptyList<Int>(), day16.invalidValues(nearbyTickets[0], exampleRules))
        assertEquals(listOf(4), day16.invalidValues(nearbyTickets[1], exampleRules))
        assertEquals(listOf(55), day16.invalidValues(nearbyTickets[2], exampleRules))
        assertEquals(listOf(12), day16.invalidValues(nearbyTickets[3], exampleRules))
    }

    @Test
    fun `can find only possible valid tickets in a list`() {
        assertEquals(listOf(nearbyTickets[0]), day16.validTickets(exampleRules, nearbyTickets))
    }

    @Test
    fun `can return all possible positions for a rule (where all valid tickets satisfy that spot)`() {
        assertEquals(listOf(1, 2), day16.possiblePositions(validRules[0], validTickets))
        assertEquals(listOf(0, 1, 2), day16.possiblePositions(validRules[1], validTickets))
        assertEquals(listOf(2), day16.possiblePositions(validRules[2], validTickets))
    }

    @Test
    fun `generates assignment based on possible locations of rules`() {
        assertEquals(
            listOf(1, 0, 2), day16.assignments(
                listOf(
                    listOf(1, 2),
                    listOf(0, 1, 2),
                    listOf(2)
                )
            )
        )
    }
}
