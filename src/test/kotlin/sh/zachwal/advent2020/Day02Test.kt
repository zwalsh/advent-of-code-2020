package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day02Test {

    private val exampleRows = listOf(
        "1-3 a: abcde",
        "1-3 b: cdefg",
        "2-9 c: ccccccccc",
    )

    private val day2 = Day02()

    @Test
    fun `count valid checks all passwords in range`() {
        assertEquals(2, day2.countValid1(exampleRows))
    }

    @Test
    fun `policy extraction regex gets range and char`() {
        assertEquals(Day02.PasswordPolicy1((1..3),'a'), day2.extractPolicy(exampleRows[0]))
    }
}
