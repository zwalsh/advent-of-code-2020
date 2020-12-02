package sh.zachwal.advent2020

fun main() {
    println(Day01().part1())
    println(Day01().part2())
}

class Day01 {
    private val inputs = input(1, 1).lines()
        .filter { it != "" }
        .map(String::toInt)

    fun part1(): Int? {
        return expenseReportTwoExpenses(inputs)
    }

    fun part2(): Int? = expenseReportThreeExpenses(inputs)

    fun expenseReportTwoExpenses(expenses: List<Int>): Int? {
        val expenseSet = mutableSetOf<Int>()

        expenses.forEach {
            val complement2020 = 2020 - it
            if (expenseSet.contains(complement2020)) {
                return it * complement2020
            }
            expenseSet.add(it)
        }
        return null
    }

    fun expenseReportThreeExpenses(expenses: List<Int>): Int? {
        expenses.forEach { first ->
            expenses.forEach { second ->
                expenses.forEach { third ->
                    if (first + second + third == 2020) {
                        return first * second * third
                    }
                }
            }
        }
        return null
    }
}
