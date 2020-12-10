package sh.zachwal.advent2020

fun main() {
    val day09 = Day09()
    val stream = input(9, 1).lines().filter { it != "" }.map { it.toLong() }
    println(day09.firstNotMatching(stream))
    println(day09.contiguousSumMatchingMissing(stream))
}

class Day09 {

    fun firstNotMatching(stream: List<Long>, windowSize: Int = 25): Long {
        return stream.windowed(windowSize + 1).firstOrNull { window ->
            val numberToCheck = window.last()
            val numsToSum = window.slice(0 until window.size - 1)
            val validSums = numsToSum.flatMap { first ->
                numsToSum.map { second ->
                    first + second
                }
            }.toSet()
            numberToCheck !in validSums
        }?.last() ?: error("No window had a non-matching number")
    }

    fun contiguousSumMatchingMissing(stream: List<Long>, windowSize: Int = 25): Long {
        val targetSum = firstNotMatching(stream, windowSize)
        val activeSets = mutableListOf<MutableList<Long>>()
        stream.forEach { nextNum ->
            activeSets.forEach {
                it.add(nextNum)
                if (it.sum() == targetSum) {
                    return it.minOrNull()!! + it.maxOrNull()!!
                }
            }
            activeSets.add(mutableListOf(nextNum))
            activeSets.removeAll { it.sum() > targetSum }
        }
        return 0
    }

    /*
    O(n) solution that adds to the end while too small & subtracts from the start while too big
    until it finds the correct range
     */
    fun contiguousSumOneActiveSet(stream: List<Long>, windowSize: Int = 25): Long {
        val targetSum = firstNotMatching(stream, windowSize)
        var startIdx = 0
        var endIdx = 1
        var curSum = stream[startIdx] + stream[endIdx]
        while (curSum != targetSum) {
            if (curSum < targetSum) {
                endIdx += 1
                curSum += stream[endIdx]
            } else if (curSum > targetSum) {
                curSum -= stream[startIdx]
                startIdx += 1
            }
        }
        val contiguousSection = stream.subList(startIdx, endIdx + 1)
        return contiguousSection.minOrNull()!! + contiguousSection.maxOrNull()!!
    }
}
