package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day18Test {
    private val day18 = Day18()
    private val numExp = "3"
    private val compoundExp = "3 + 4"
    private val parensExp = " 1 + (3 + 4)"
    private val multipleExp = "3 * 4 + 5"
    private val ex1 = "2 * 3 + (4 * 5)"
    private val ex2 = "5 + (8 * 3 + 9 + 3 * 4 * 3)"
    private val ex3 = "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"
    private val ex4 = "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"

    @Test
    fun `parse number`() {
        assertEquals(Num(3), day18.parseExpression(numExp))
    }

    @Test
    fun `parse simple compound`() {
        assertEquals(Compound(Num(3), Num(4), Operation.ADD), day18.parseExpression(compoundExp))
    }

    @Test
    fun `parse with parens`() {
        assertEquals(
            Compound(Num(1), Compound(Num(3), Num(4), Operation.ADD), Operation.ADD),
            day18.parseExpression(parensExp)
        )
    }

    @Test
    fun `parse multiple expressions`() {
        assertEquals(
            Compound(Compound(Num(3), Num(4), Operation.MUL), Num(5), Operation.ADD),
            day18.parseExpression(multipleExp)
        )
    }

    @Test
    fun `parse precede addition`() {
        assertEquals(
            Compound(Num(3), Compound(Num(4), Num(5), Operation.ADD), Operation.MUL),
            day18.parseExpression(multipleExp, splitFirst = listOf(Operation.MUL))
        )
        // 2 * 3 + (4 * 5)
        assertEquals(
            Compound(Num(2), Compound(Num(3), Compound(Num(4), Num(5), Operation.MUL), Operation.ADD), Operation.MUL),
            day18.parseExpression(ex1, splitFirst = listOf(Operation.MUL))
        )
        // 8 * 3 + 9 + 3 * 4 * 3
        assertEquals(
            Compound(
//                Num(8),
//                Compound(
//                    Compound(Compound(Num(3), Num(9), Operation.ADD), Num(3), Operation.ADD),
//                    Compound(Num(4), Num(3), Operation.MUL),
//                    Operation.MUL
//                ),
                Compound(
                    Compound(
                        Num(8),
                        Compound(Compound(Num(3), Num(9), Operation.ADD), Num(3), Operation.ADD),
                        Operation.MUL
                    ),
                    Num(4),
                    Operation.MUL
                ),
                Num(3),
                Operation.MUL
            ),
            day18.parseExpression("8 * 3 + 9 + 3 * 4 * 3", splitFirst = listOf(Operation.MUL))
        )
    }

    @Test
    fun `evaluates parsed expressions`() {
        assertEquals(26, day18.parseExpression(ex1).eval())
        assertEquals(437, day18.parseExpression(ex2).eval())
        assertEquals(12240, day18.parseExpression(ex3).eval())
        assertEquals(13632, day18.parseExpression(ex4).eval())
    }

    @Test
    fun `evaluates addition-first parsed expressions`() {
        assertEquals(46, day18.parseExpression(ex1, splitFirst = listOf(Operation.MUL)).eval())
        assertEquals(1440, day18.parseExpression("8 * 3 + 9 + 3 * 4 * 3", splitFirst = listOf(Operation.MUL)).eval())
        val ex2expr = day18.parseExpression(ex2, splitFirst = listOf(Operation.MUL))
        assertEquals(1445, ex2expr.eval())
        assertEquals(669060, day18.parseExpression(ex3, splitFirst = listOf(Operation.MUL)).eval())
        assertEquals(23340, day18.parseExpression(ex4, splitFirst = listOf(Operation.MUL)).eval())
    }
}
