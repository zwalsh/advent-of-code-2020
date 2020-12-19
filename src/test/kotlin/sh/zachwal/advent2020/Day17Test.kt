package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day17Test {
    private val day17 = Day17()

    /*
    .#.
    ..#
    ###
     */
    private val examplePlane = listOf(
        listOf(false, true, false),
        listOf(false, false, true),
        listOf(true, true, true)
    )
    private val initialPoints = setOf(
        Point(0, 1, 0),
        Point(1, 2, 0),
        Point(2, 0, 0),
        Point(2, 1, 0),
        Point(2, 2, 0)
    )

    @Test
    fun `can build initial world`() {
        assertEquals(initialPoints, day17.initialWorld(examplePlane))
    }

    @Test
    fun `can step once`() {
        /*
            z=-1
            #..
            ..#
            .#.

            z=0
            #.#
            .##
            .#.

            z=1
            #..
            ..#
            .#.
         */
        val step1 = setOf(
            Point(0, 0, -1),
            Point(1, 2, -1),
            Point(2, 1, -1),
            Point(0, 0, 0),
            Point(0, 2, 0),
            Point(1, 1, 0),
            Point(1, 2, 0),
            Point(2, 1, 0),
            Point(0, 0, 1),
            Point(1, 2, 1),
            Point(2, 1, 1),
        ).map {
            Point(it.x + 1, it.y, it.z) // translate because of confusing example
        }


        val actualStep1 = day17.step(initialPoints)
        (step1 - actualStep1).forEach {
            println("Missing $it")
        }
        (actualStep1 - step1).forEach {
            println("Including $it")
        }

        assertTrue(step1.equals(actualStep1))
    }

    @Test
    fun `can accurately count all neighbors`() {
        val startingPoint = Point(1, 1, 0)
        val neighborCountsOnePoint = day17.neighborCounts(setOf(startingPoint))
        assertEquals(26, neighborCountsOnePoint.size)
        (0..2).forEach { x ->
            (0..2).forEach { y ->
                (-1..1).forEach { z ->
                    val p = Point(x, y, z)
                    if (p == startingPoint) {
                        assertNull(neighborCountsOnePoint[p], "$p had incorrect count")
                    } else {
                        assertEquals(1, neighborCountsOnePoint[p], "$p had incorrect count")
                    }
                }
            }
        }
    }

    @Test
    fun `can step twice`() {
        /*
            z=-2
            .....
            .....
            ..#..
            .....
            .....

            z=-1
            ..#..
            .#..#
            ....#
            .#...
            .....


        /*
            z=0
            ##...
            ##...
            #....
            ....#
            .###.

         */


            z=1
            ..#..
            .#..#
            ....#
            .#...
            .....

            z=2
            .....
            .....
            ..#..
            .....
            .....
         */
        val step2 = setOf(
            Point(1, 1, -2),
            Point(1, 1, 2),

            Point(-1, 1, -1),
            Point(0, 0, -1),
            Point(0, 3, -1),
            Point(1, 3, -1),
            Point(2, 0, -1),

            Point(-1, 1, 1),
            Point(0, 0, 1),
            Point(0, 3, 1),
            Point(1, 3, 1),
            Point(2, 0, 1),

            Point(-1, -1, 0),
            Point(-1, 0, 0),
            Point(0, -1, 0),
            Point(0, 0, 0),
            Point(1, -1, 0),
            Point(2, 3, 0),
            Point(3, 0, 0),
            Point(3, 1, 0),
            Point(3, 2, 0),
        )
        assertEquals(step2, day17.step(day17.step(initialPoints)))
    }
}
