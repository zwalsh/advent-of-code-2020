package sh.zachwal.advent2020

fun main() {
    val day17 = Day17()
    val input = input(17, 1).lines().filter { it != "" }.map { it.toCharArray().toList() }
    println(day17.totalActive(charsToBools(input)))
}

fun charsToBools(chars: List<List<Char>>) = chars.map { row ->
    row.map { it == '#' }
}

data class Point(val x: Int, val y: Int, val z: Int, val a: Int = 0) {
    companion object {
        val ORIGIN = Point(0, 0, 0, 0)
    }
}

class Day17 {

    fun initialWorld(plane: List<List<Boolean>>): Set<Point> = plane.foldIndexed(
        emptySet()
    ) { x, points, bools ->
        bools.foldIndexed(points) { y, rowPoints, b ->
            if (b) {
                rowPoints + setOf(Point(x, y, 0, 0))
            } else {
                rowPoints
            }
        }
    }

    private val steps = (-1..1)
    private val neighborVectorPoints = steps.flatMap { x ->
        steps.flatMap { y ->
            steps.flatMap { z ->
                steps.map {
                    Point(x, y, z, it)
                }
            }
        }
    }.filter {
        it != Point.ORIGIN
    }

    fun step(actives: Set<Point>): Set<Point> {
        val neighborCounts = neighborCounts(actives)

        val inactiveToActive = neighborCounts.filterValues {
            it == 3
        }.keys

        val stayActive = actives.filter {
            neighborCounts[it] in setOf(2, 3)
        }

        return inactiveToActive + stayActive
    }

    fun neighborCounts(actives: Set<Point>): Map<Point, Int> {
        return actives.flatMap { p ->
            neighborVectorPoints.map { v ->
                Point(p.x + v.x, p.y + v.y, p.z + v.z, p.a + v.a)
            }
        }.groupBy {
            it
        }.map {
            it.key to it.value.size
        }.toMap()
    }

    fun totalActive(initialPlane: List<List<Boolean>>, steps: Int = 6): Int {
        var active = initialWorld(initialPlane)
        (0 until steps).forEach {
            active = step(active)
        }
        return active.size
    }
}
