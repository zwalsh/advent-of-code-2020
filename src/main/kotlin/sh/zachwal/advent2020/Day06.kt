package sh.zachwal.advent2020

fun main() {
    val input = input(6, 1).split("\n\n").map {
        it.split("\n").filter { word -> word != "" }
    }
    val day06 = Day06()
    println(day06.totalYesCount(input))
    println(day06.allYesCount(input))
}

class Day06 {

    private fun uniqueChars(answers: List<String>): Int = answers.flatMap {
        it.toCharArray().asIterable()
    }.distinct().count()

    fun sharedChars(answers: List<String>): Int = answers.map {
        it.toCharArray().toSet()
    }.reduce { acc, next ->
        acc.intersect(next)
    }.size

    fun totalYesCount(answers: List<List<String>>): Int = answers.sumBy(this::uniqueChars)

    fun allYesCount(answers: List<List<String>>): Int = answers.sumBy(this::sharedChars)

}
