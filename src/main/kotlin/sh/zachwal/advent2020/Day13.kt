package sh.zachwal.advent2020

fun main() {
    val day13 = Day13()
    val input = input(13, 1)
    val lines = input.lines().filter { it != "" }
    val time = lines[0].toInt()
    val buses = lines[1].split(",").filter { it != "x" }.map { it.toInt() }
    println(day13.firstBusAfterTime(time, buses))
    println(buses.max())
    println(day13.earliestTimestampMatchingSchedule(lines[1].split(",")))
}

class Day13 {

    private fun waitTime(time: Int, bus: Int): Int {
        val multiple = time / bus
        val firstArrivalAfter = (multiple + 1) * bus
        return firstArrivalAfter - time
    }

    fun firstBusAfterTime(time: Int, buses: List<Int>): Int {
        return buses.minByOrNull {
            waitTime(time, it)
        } ?: error("Could not find min by first arrival")
    }

    data class BusArrival(
        val frequency: Int,
        val offset: Int
    ) {
        fun arrivesAtTPlusOffset(t: Long): Boolean = ((t + offset) % frequency) == 0L
    }

    private fun lcm(nums: List<Long>): Long {
        val max = nums.maxOrNull()?.toLong()
        var curCandidate = max ?: error("empty list ??")
        while (!nums.all { curCandidate % it == 0L }) {
            curCandidate += max
        }
        return curCandidate
    }

    fun earliestTimestampMatchingSchedule(schedule: List<String>): Long {
        var unsatisfiedArrivals = schedule.mapIndexed { index, s ->
            index to s
        }.filter {
            it.second != "x"
        }.map {
            BusArrival(
                it.second.toInt(),
                it.first
            )
        }
        val longestSchedule = unsatisfiedArrivals.maxByOrNull { it.frequency } ?: error("no schedules??")
        var t = longestSchedule.frequency.toLong() - longestSchedule.offset
        var curLcm = longestSchedule.frequency.toLong()

        while (unsatisfiedArrivals.isNotEmpty()) {
            while (!unsatisfiedArrivals.any { it.arrivesAtTPlusOffset(t) }) {
                t += curLcm
            }
            val satisfiedSchedules = unsatisfiedArrivals.filter { it.arrivesAtTPlusOffset(t) }.map { it.frequency.toLong() }
            unsatisfiedArrivals = unsatisfiedArrivals.filter { !it.arrivesAtTPlusOffset(t) }
            curLcm = lcm(listOf(curLcm) + satisfiedSchedules)
        }
        return t
    }
}

