package sh.zachwal.advent2020

fun main() {
    val day16 = Day16()
    val input = input(16, 1)
    val sections = input.split("\n\n")
    val rules = sections[0].lines().filter { it != "" }.map { day16.parseRule(it) }
    val myTicket = sections[1].lines()[1].split(",").map { it.toInt() }
    val nearbyTickets = sections[2].lines().drop(1).filter { it != "" }.map { line ->
        line.split(",").map { it.toInt() }
    }
    println(day16.sumAllInvalid(rules, nearbyTickets))
    val assignments = day16.assignments(rules, nearbyTickets)
    println(assignments)
    println(day16.sumOfDepartureFields(rules, assignments, myTicket))
}

class Day16 {
    data class FieldRule(
        val name: String,
        val validNumbers: Set<Int>
    ) {
        fun isSatisfiedBy(num: Int): Boolean = num in validNumbers
    }

    fun parseRule(rule: String): FieldRule {
        val name = rule.substringBefore(":")
        val ranges = rule.substringAfter(": ")
            .split(" or ")
            .map {
                val rangeBoundaries = it.split("-")
                IntRange(rangeBoundaries[0].toInt(), rangeBoundaries[1].toInt())
            }
        val validNumbers = ranges.fold(setOf<Int>()) { nums, nextRange ->
            nums + nextRange.toSet()
        }
        return FieldRule(name, validNumbers)
    }

    fun sumAllInvalid(rules: List<FieldRule>, tickets: List<List<Int>>): Int {
        return tickets.sumBy { t ->
            invalidValues(t, rules).sum()
        }
    }

    fun invalidValues(ticket: List<Int>, rules: List<FieldRule>): List<Int> = ticket.filter { num ->
        rules.none { r ->
            r.isSatisfiedBy(num)
        }
    }

    fun validTickets(rules: List<FieldRule>, tickets: List<List<Int>>): List<List<Int>> {
        return tickets.filter { t ->
            t.all { num ->
                rules.any { r ->
                    r.isSatisfiedBy(num)
                }
            }
        }
    }

    fun possiblePositions(rule: FieldRule, tickets: List<List<Int>>): List<Int> {
        val allIndices = tickets[0].indices
        return allIndices.filter { i ->
            tickets.map { t ->
                t[i]
            }.all { num ->
                rule.isSatisfiedBy(num)
            }
        }
    }

    fun assignments(possiblePositions: List<List<Int>>): List<Int> {
        return if (possiblePositions.all { it.size == 1 }) {
            possiblePositions.flatten()
        } else {
            val fixedPositions = possiblePositions.filter { it.size == 1 }.map { it.single() }
            val possibleWithFixedRemoved = possiblePositions.map {
                if (it.size == 1) {
                    it
                } else {
                    it.filter { poss ->
                        poss !in fixedPositions
                    }
                }
            }
            assignments(possibleWithFixedRemoved)
        }
    }

    fun assignments(rules: List<FieldRule>, tickets: List<List<Int>>): List<Int> {
        val validTickets = validTickets(rules, tickets)
        val possiblePositions = rules.map { r ->
            possiblePositions(r, validTickets)
        }
        return assignments(possiblePositions)
    }

    fun sumOfDepartureFields(rules: List<FieldRule>, assignments: List<Int>, ticket: List<Int>): Long {
        val indicesOfDepartureFieldsInTicket = rules.mapIndexed { index, fieldRule ->
            fieldRule to index
        }.filter {
            it.first.name.startsWith("departure")
        }.map {
            assignments[it.second]
        }
        return indicesOfDepartureFieldsInTicket.map {
            ticket[it]
        }.fold(1L) { acc, it ->
            acc * it
        }
    }
}
