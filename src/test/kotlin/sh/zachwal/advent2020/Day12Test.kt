package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day12Test {
    private val day12 = Day12()
    private val instructions = listOf(
        "F10",
        "N3",
        "F7",
        "R90",
        "F11",
    )
    private val startingState = Day12.TurtleState(0, 0, 0)

    @Test
    fun `calculates manhattan distance`() {
        assertEquals(25, day12.manhattanDistance(instructions))
    }

    @Test
    fun `parses instructions`() {
        assertEquals(Day12.Instruction(Day12.Action.F, 10), day12.parseInstruction(instructions[0]))
    }

    @Test
    fun `can step one instruction at a time`() {
        val firstInstruction = day12.parseInstruction(instructions[0])
        assertEquals(Day12.TurtleState(10, 0, 0), day12.turtleStep(startingState, firstInstruction))
    }

    @Test
    fun `waypoint manhattan distance`() {
        assertEquals(286, day12.waypointManhattanDistance(instructions))
    }
}

