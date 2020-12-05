package sh.zachwal.advent2020

fun main() {
    val input = input(5, 1).lines().filter { it != "" }
    val day05 = Day05()
    val maxSeatId = input.maxOf(day05::seatId)
    println(maxSeatId)
    println(day05.findMissingSeat(input))
}

class Day05 {

    private fun calcStepInRange(range: IntRange, top: Boolean): IntRange {
        val size = range.last - range.first + 1
        val mid = range.first + size / 2
        return if (top) {
            range.first until mid
        } else {
            mid..range.last
        }
    }

    fun calcRow(pass: String): Int = pass.substring(0..6).fold(0..127) { range, char ->
        calcStepInRange(range, char == 'F')
    }.first


    fun calcCol(pass: String): Int = pass.substring(7..9).fold(0..7) { range, char ->
        calcStepInRange(range, char == 'L')
    }.first

    fun seatId(pass: String) = seatIdFromRowAndCol(calcRow(pass), calcCol(pass))

    private fun seatIdFromRowAndCol(row: Int, col: Int) = row * 8 + col

    fun findMissingSeat(passes: List<String>): Int {
        val seats = passes.map(::seatId).toSet()
        val allSeatIds = (0..127).flatMap {  row ->
            (0..7).map { col ->
                seatIdFromRowAndCol(row, col)
            }
        }
        return allSeatIds.first { it !in seats && (it - 1) in seats && (it + 1) in seats }
    }
}
