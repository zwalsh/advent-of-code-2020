package sh.zachwal.advent2020

fun main() {
    val day14 = Day14()
    val input = input(14, 1).lines().filter { it != "" }
    println(day14.sumOfMemory(input))
    println(day14.memoryWithAddressMasking(input).values.sum())
}

class Day14 {

    private val assignmentRegex = Regex("mem\\[(\\d+)] = (\\d+)")
    private fun parseAssignment(input: String): Pair<Long, Long>? {
        val match = assignmentRegex.matchEntire(input)
        return match?.groupValues?.let {
            it[1].toLong() to it[2].toLong()
        }
    }

    private fun parseMask(input: String) = input.substringAfter("= ")

    fun maskedAddresses(startingAddress: Long, mask: String): List<Long> {
        val binaryStartingAddress = startingAddress.toString(2).padStart(mask.length, '0')
        return mask.toCharArray().foldIndexed(listOf("")) { i, acc, char ->
            val charsToAdd = when (char) {
                '0' -> listOf(binaryStartingAddress[i])
                '1' -> listOf('1')
                'X' -> listOf('0', '1')
                else -> error("Unexpected mask char $char")
            }
            acc.flatMap { accumulatingAddr ->
                charsToAdd.map { c ->
                    accumulatingAddr + c
                }
            }
        }.map { it.toLong(2) }
    }

    fun memoryWithAddressMasking(actions: List<String>): Map<Long, Long> {
        val memory = mutableMapOf<Long, Long>()
        lateinit var mask: String
        actions.forEach { a ->
            parseAssignment(a)?.let { assignment ->
                val addresses = maskedAddresses(assignment.first, mask)
                addresses.forEach {
                    memory[it] = assignment.second
                }
            } ?: run {
                mask = parseMask(a)
            }
        }

        return memory
    }

    fun sumOfMemory(actions: List<String>): Long {
        val memory = mutableMapOf<Long, Long>()
        var mask = parseMask(actions.first())
        actions.forEach { a ->
            parseAssignment(a)?.let {
                val maskedValue = applyBitmask(mask, it.second)
                println("Setting ${it.first} = ${it.second} with mask $mask => $maskedValue")
                memory[it.first] = maskedValue
            } ?: run {
                mask = parseMask(a)
            }
        }
        return memory.values.sum()
    }

    fun applyBitmask(mask: String, num: Long): Long {
        val asBinary = num.toString(2)
        val paddedBinary = asBinary.padStart(mask.length, '0')
        return mask.toCharArray().foldIndexed(paddedBinary) { index, acc, charMask ->
            when (charMask) {
                '0', '1' -> {
                    val chars = acc.toCharArray()
                    chars[index] = charMask
                    String(chars)
                }
                else -> acc
            }
        }.toLong(2)
    }
}
