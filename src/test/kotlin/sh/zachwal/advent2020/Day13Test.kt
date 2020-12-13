package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day13Test {
    private val day13 = Day13()
    private val timestamp = 939
    private val buses = "7,13,x,x,59,x,31,19"
    private val busTimes = buses.split(",").filter { it != "x" }.map { it.toInt() }

    @Test
    fun `can find first bus after timestamp`() {
        assertEquals(59, day13.firstBusAfterTime(timestamp, busTimes))
    }

    @Test
    fun `can find earliest timestamp matching all buses`() {
        assertEquals(1068781, day13.earliestTimestampMatchingSchedule(buses.split(",")))
    }
}
