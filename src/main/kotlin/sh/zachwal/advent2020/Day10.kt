package sh.zachwal.advent2020

fun main() {
    val joltages = input(10, 1).lines().filterNot { it == "" }.map { it.toLong() }
    val day10 = Day10()
    println(day10.joltageDifferences(joltages))
    println(day10.joltageArrangements(joltages))
}

class Day10 {


    fun joltageDifferences(joltages: List<Long>): Long {
        val joltagesSorted = allJoltagesSorted(joltages)
        val differences = joltagesSorted.windowed(2).map { it.last() - it.first() }
        return differences.count { it == 1L } * differences.count { it == 3L }.toLong()
    }

    fun joltageArrangements(joltages: List<Long>): Long {
        return arrangementsToTargetFrom(0, allJoltagesSorted(joltages))
    }

    private val memoizedArrangements = mutableMapOf<Int, Long>()

    private fun arrangementsToTargetFrom(index: Int, sortedJoltages: List<Long>): Long {
        if (index == sortedJoltages.lastIndex) {
            return 1
        }
        if (memoizedArrangements.containsKey(index)) {
            return memoizedArrangements[index] ?: error("Not in memoized map")
        }
        val curJoltage = sortedJoltages[index]
        val sum = sortedJoltages.reduceIndexed { i, sum, joltage ->
            if (curJoltage < joltage && joltage <= curJoltage + 3) {
                sum + arrangementsToTargetFrom(i, sortedJoltages)
            } else {
                sum
            }
        }
        memoizedArrangements[index] = sum
        return sum
    }

    private fun allJoltagesSorted(joltages: List<Long>) =
        listOf(0L) + joltages.sorted() + listOf((joltages.maxOrNull() ?: 0) + 3)
}
