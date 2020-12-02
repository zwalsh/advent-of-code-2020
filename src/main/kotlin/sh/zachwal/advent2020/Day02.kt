package sh.zachwal.advent2020

import kotlin.system.exitProcess

fun main() {
    println(
        Day02().countValid1(input(2, 1).lines()
            .filter { it != "" })
    )
    println(
        Day02().countValid2(input(2, 1).lines()
            .filter { it != "" })
    )
}

class Day02 {
    data class PasswordPolicy1(val range: IntRange, val char: Char) {
        fun isValid(password: String) = password.count { it == char } in range
    }

    data class PasswordPolicy2(val positionOne: Int, val positionTwo: Int, val char: Char) {
        fun isValid(password: String) = (password[positionOne] == char).xor(
            password[positionTwo] == char
        )
    }

    private val passwordRowRegex = Regex("(\\d+)-(\\d+) (\\w): (\\w+)")

    fun extractPolicy(row: String): PasswordPolicy1 {
        val matchedGroups = matchedGroups(row)
        return PasswordPolicy1(
            IntRange(matchedGroups[1].toInt(), matchedGroups[2].toInt()),
            matchedGroups[3].toCharArray()[0]
        )
    }

    fun extractPolicy2(row: String): PasswordPolicy2 {
        val matchedGroups = matchedGroups(row)
        return PasswordPolicy2(
            matchedGroups[1].toInt() - 1,
            matchedGroups[2].toInt() - 1,
            matchedGroups[3].toCharArray()[0]
        )
    }

    private fun matchedGroups(row: String): List<String> {
        val match: MatchResult?
        try {
            match = passwordRowRegex.matchEntire(row)
            return match!!.groupValues
        } catch (e: NullPointerException) {
            println(e)
            exitProcess(1)
        }
    }

    fun extractPassword(row: String): String = passwordRowRegex.matchEntire(row)!!.groupValues[4]

    fun countValid1(rows: List<String>): Int = rows.count { row ->
        extractPolicy(row).isValid(extractPassword(row))
    }

    fun countValid2(rows: List<String>): Int = rows.count {
        extractPolicy2(it).isValid(extractPassword(it))
    }
}
