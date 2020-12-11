package sh.zachwal.advent2020

fun main() {
    val day11 = Day11()
    val inputRows = input(11, 1).lines().filter { it != "" }
    val grid = day11.parse(inputRows)
    println(day11.occupiedCount(grid))
    println(day11.occupiedCount(grid, day11::countVisibleOccupied, 5))
}

class Day11 {
    enum class Square {
        FLOOR,
        EMPTY,
        OCCUPIED
    }

    fun parse(rows: List<String>): List<List<Square>> = rows.map {
        it.toCharArray().map { c ->
            when (c) {
                '.' -> Square.FLOOR
                'L' -> Square.EMPTY
                '#' -> Square.OCCUPIED
                else -> error("Unknown square $c")
            }
        }
    }

    private val sightVectors = listOf(
        listOf(-1, -1),
        listOf(-1, 0),
        listOf(-1, 1),
        listOf(0, -1),
        listOf(0, 1),
        listOf(1, -1),
        listOf(1, 0),
        listOf(1, 1)
    )

    fun List<List<Square>>.getOrNull(row: Int, col: Int): Square? = getOrNull(row)?.getOrNull(col)

    fun countAdjacentOccupied(row: Int, col: Int, seats: List<List<Square>>): Int {
        return sightVectors.count { vec ->
            seats.getOrNull(vec[0] + row, vec[1] + col) == Square.OCCUPIED
        }
    }

    fun countVisibleOccupied(row: Int, col: Int, seats: List<List<Square>>): Int {
        return sightVectors.count { vec ->
            var curVisRow = row + vec[0]
            var curVisCol = col + vec[1]
            var curVisSeat = seats.getOrNull(curVisRow, curVisCol)
            while (curVisSeat == Square.FLOOR) {
                curVisRow += vec[0]
                curVisCol += vec[1]
                curVisSeat = seats.getOrNull(curVisRow, curVisCol)
            }
            curVisSeat == Square.OCCUPIED
        }
    }

    private fun step(
        seats: List<List<Square>>,
        countFn: (Int, Int, List<List<Square>>) -> Int,
        visThreshold: Int
    ): List<List<Square>> {
        return seats.mapIndexed { row, squares ->
            squares.mapIndexed { col, square ->
                when (square) {
                    Square.FLOOR -> Square.FLOOR
                    Square.EMPTY -> if (countFn(row, col, seats) == 0) Square.OCCUPIED else Square.EMPTY
                    Square.OCCUPIED -> if (countFn(row, col, seats) >= visThreshold) Square.EMPTY else Square.OCCUPIED
                }
            }
        }
    }

    private fun equal(seats1: List<List<Square>>, seats2: List<List<Square>>): Boolean {
        seats1.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, square ->
                if (square != seats2[rowIndex][colIndex]) {
                    return false
                }
            }
        }
        return true
    }

    fun occupiedCount(
        seats: List<List<Square>>,
        countFn: (Int, Int, List<List<Square>>) -> Int = this::countAdjacentOccupied,
        visThreshold: Int = 4
    ): Int {
        var curSeats = seats
        var nextSeats = step(seats, countFn, visThreshold)
        while (!equal(curSeats, nextSeats)) {
            curSeats = nextSeats
            nextSeats = step(curSeats, countFn, visThreshold)
        }
        return curSeats.flatten().count { it == Square.OCCUPIED }
    }
}
