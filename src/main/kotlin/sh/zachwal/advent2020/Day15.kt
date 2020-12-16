package sh.zachwal.advent2020

fun main() {
    val day15 = Day15()
    val memGame = listOf(6, 19, 0, 5, 7, 13, 1)
    println(day15.memGameToSize(memGame, 2020).last())
    println(day15.memGameToSize(memGame, 30000000).last())
}

class Day15 {
    fun memGameToSize(starting: List<Int>, size: Int): List<Int> {
        val memGame = starting.toMutableList()
        val mostRecentIndex = memGame.dropLast(1).mapIndexed { i, num -> num to i }.toMap().toMutableMap()
        while (memGame.size < size) {
            val next = mostRecentIndex[memGame.last()]?.let { memGame.size - it - 1 } ?: 0
            mostRecentIndex[memGame.last()] = memGame.lastIndex
            memGame.add(next)
        }
        return memGame
    }
}

