package sh.zachwal.advent2020

fun main() {
    println(Day03().countTrees(input(3, 1).lines().filter { it != "" }))
    println(Day03().countMultipleSlopes(input(3, 1).lines().filter { it != "" }))
}

class Day03 {

    fun countMultipleSlopes(rows: List<String>): Long {
        val countTrees = countTrees(rows, 1, 1).toLong()
        val countTrees1 = countTrees(rows, 3, 1)
        val countTrees2 = countTrees(rows, 5, 1)
        val countTrees3 = countTrees(rows, 7, 1)
        val countTrees4 = countTrees(rows, 1, 2)
        return countTrees *
                countTrees1 *
                countTrees2 *
                countTrees3 *
                countTrees4
    }

    fun countTrees(rows: List<String>, horizontalSlope: Int = 3, verticalSlope: Int = 1): Int {
        return rows.withIndex().sumBy { iv ->
            val row = iv.value
            val position = (iv.index * horizontalSlope / verticalSlope) % row.length
            val char = row[position]
            if (char == '#' && iv.index % verticalSlope == 0) 1 else 0
        }
    }
}
