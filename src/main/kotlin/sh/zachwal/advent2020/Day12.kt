package sh.zachwal.advent2020

import kotlin.math.abs

fun main() {
    val day12 = Day12()
    val instructions = input(12, 1).lines().filter { it != "" }
    println(day12.manhattanDistance(instructions))
    println(day12.waypointManhattanDistance(instructions))
}

class Day12 {
    data class TurtleState(
        val x: Int,
        val y: Int,
        val heading: Int // in Pi/2 radians (fourths of a circle)
    )

    data class WaypointState(
        val shipX: Int,
        val shipY: Int,
        val waypointX: Int,
        val waypointY: Int
    )

    enum class Action {
        N,
        S,
        E,
        W,
        L,
        R,
        F
    }

    data class Instruction(
        val action: Action,
        val amount: Int
    )

    fun parseInstruction(instruction: String): Instruction =
        Instruction(
            Action.valueOf(instruction[0].toUpperCase().toString()),
            instruction.substring(1).toInt()
        )

    fun manhattanDistance(instructions: List<String>): Int {
        val finalState = instructions.map(this::parseInstruction).fold(TurtleState(0, 0, 0), this::turtleStep)
        return abs(finalState.x) + abs(finalState.y)
    }

    fun turtleStep(state: TurtleState, instruction: Instruction): TurtleState {
        return when (instruction.action) {
            Action.N -> state.copy(y = state.y + instruction.amount)
            Action.S -> state.copy(y = state.y - instruction.amount)
            Action.E -> state.copy(x = state.x + instruction.amount)
            Action.W -> state.copy(x = state.x - instruction.amount)
            Action.L -> state.copy(heading = (state.heading + instruction.amount / 90 + 4) % 4)
            Action.R -> state.copy(heading = (state.heading - instruction.amount / 90 + 4) % 4)
            Action.F -> {
                val xDiff = when (state.heading) {
                    0 -> instruction.amount
                    1 -> 0
                    2 -> -instruction.amount
                    3 -> 0
                    else -> error("Invalid heading ${state.heading}")
                }
                val yDiff = when (state.heading) {
                    0 -> 0
                    1 -> instruction.amount
                    2 -> 0
                    3 -> -instruction.amount
                    else -> error("Invalid heading ${state.heading}")
                }
                state.copy(x = state.x + xDiff, y = state.y + yDiff)
            }
        }
    }

    fun waypointManhattanDistance(instructions: List<String>): Int {
        val finalState = instructions.map(this::parseInstruction).fold(WaypointState(0, 0, 10, 1), this::waypointStep)
        return abs(finalState.shipX) + abs(finalState.shipY)
    }

    fun waypointStep(state: WaypointState, instruction: Instruction): WaypointState {
        return when (instruction.action) {
            Action.N -> state.copy(waypointY = state.waypointY + instruction.amount)
            Action.S -> state.copy(waypointY = state.waypointY - instruction.amount)
            Action.E -> state.copy(waypointX = state.waypointX + instruction.amount)
            Action.W -> state.copy(waypointX = state.waypointX - instruction.amount)
            Action.L -> {
                val newX = when(instruction.amount) {
                    90 -> -state.waypointY
                    180 -> -state.waypointX
                    270 -> state.waypointY
                    else -> error("Unknown rotation ${instruction.amount}")
                }
                val newY = when(instruction.amount) {
                    90 -> state.waypointX
                    180 -> -state.waypointY
                    270 -> -state.waypointX
                    else -> error("Unknown rotation ${instruction.amount}")
                }
                state.copy(waypointX = newX, waypointY = newY)
            }
            Action.R -> {
                val newX = when(instruction.amount) {
                    270 -> -state.waypointY
                    180 -> -state.waypointX
                    90 -> state.waypointY
                    else -> error("Unknown rotation ${instruction.amount}")
                }
                val newY = when(instruction.amount) {
                    270 -> state.waypointX
                    180 -> -state.waypointY
                    90 -> -state.waypointX
                    else -> error("Unknown rotation ${instruction.amount}")
                }
                state.copy(waypointX = newX, waypointY = newY)
            }
            Action.F -> state.copy(
                shipX = state.shipX + instruction.amount * state.waypointX,
                shipY = state.shipY + instruction.amount * state.waypointY
            )
        }
    }

}

