package sh.zachwal.advent2020

fun main() {
    val input = input(8, 1)
    val day08 = Day08()
    val instructions = input.lines().filter { it != "" }.map { day08.parseInstruction(it) }
    println(day08.execute(instructions))
    println(day08.fixExecution(instructions))
}

class Day08 {

    enum class InstructionAction {
        ACC,
        JMP,
        NOP;
    }

    data class Instruction(val action: InstructionAction, val value: Int)

    data class ProgramResult(val looped: Boolean, val terminalValue: Int)

    fun parseInstruction(string: String): Instruction = Instruction(
        InstructionAction.valueOf(string.substring(0..2).toUpperCase()),
        string.substringAfter(" ").toInt()
    )

    fun execute(instructions: List<Instruction>): ProgramResult {
        var acc = 0
        var index = 0
        val visitedIndices = mutableSetOf<Int>()
        while (index < instructions.size) {
            if (index in visitedIndices) {
                return ProgramResult(true, acc)
            }
            visitedIndices.add(index)
            val nextInstruction = instructions[index]
            when (nextInstruction.action) {
                InstructionAction.ACC -> {
                    acc += nextInstruction.value
                    index += 1
                }
                InstructionAction.JMP -> index += nextInstruction.value
                InstructionAction.NOP -> index += 1
            }
        }
        return ProgramResult(false, acc)
    }

    fun fixExecution(instructions: List<Instruction>): Int {
        instructions.forEachIndexed { index, instruction ->
            val copy = instructions.toMutableList()
            when (instruction.action) {
                InstructionAction.NOP -> copy[index] = Instruction(InstructionAction.JMP, instruction.value)
                InstructionAction.JMP -> copy[index] = Instruction(InstructionAction.NOP, instruction.value)
                InstructionAction.ACC -> Unit
            }
            val result = execute(copy)
            if (!result.looped) {
                return result.terminalValue
            }
        }
        error("Could not fix corrupted program")
    }
}
