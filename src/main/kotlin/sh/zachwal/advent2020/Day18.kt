package sh.zachwal.advent2020

fun main() {
    val day18 = Day18()
    val input = input(18, 1).lines().filter { it != "" }
    println(input.map { day18.parseExpression(it) }.map { it.eval() }.sum())
    println(input.map { day18.parseExpression(it, splitFirst = listOf(Operation.MUL)) }.map { it.eval() }.sum())
}

/*
Expression is one of:
- Number
- (leftExp, rightExp, op)
 */
enum class Operation {
    ADD,
    SUB,
    MUL
}

interface Expression {
    fun eval(): Long
}

data class Num(private val num: Long) : Expression {
    override fun eval(): Long = num
}

data class Compound(
    private val leftExp: Expression,
    private val rightExp: Expression,
    private val op: Operation
) : Expression {
    override fun eval(): Long = when (op) {
        Operation.ADD -> leftExp.eval() + rightExp.eval()
        Operation.SUB -> leftExp.eval() - rightExp.eval()
        Operation.MUL -> leftExp.eval() * rightExp.eval()
    }
}


class Day18 {

    fun matchingParenIndex(exprString: String): Int {
        var unclosedParenCount = 0
        exprString.reversed().forEachIndexed { index, c ->
            if (c == ')') {
                unclosedParenCount += 1
            }
            if (c == '(') {
                unclosedParenCount -= 1
                if (unclosedParenCount == 0) {
                    return exprString.length - index - 1
                }
            }
        }
        error("Unbalanced parens in expression: $exprString")
    }

    private fun opFromChar(c: Char): Operation = when (c) {
        '+' -> Operation.ADD
        '-' -> Operation.SUB
        '*' -> Operation.MUL
        else -> error("Unknown op $c")
    }

    private fun indexOfCharAtTopLevel(exprString: String, target: Char): Int {
        var openParenCount = 0
        var maxIdx = -1
        exprString.forEachIndexed { i, c ->
            when {
                c == '(' -> openParenCount++
                c == ')' -> openParenCount--
                c == target && openParenCount == 0 -> maxIdx = i
            }
        }
        return maxIdx
    }

    private fun splitIdx(exprString: String, splitFirst: List<Operation>): Int {
        val addIdx = indexOfCharAtTopLevel(exprString, '+')
        val subIdx = indexOfCharAtTopLevel(exprString, '-')
        val mulIdx = indexOfCharAtTopLevel(exprString, '*')
        val splitCandidates = mutableListOf<Int>()
        if (Operation.ADD in splitFirst && addIdx != -1) {
            splitCandidates.add(addIdx)
        }
        if (Operation.SUB in splitFirst && addIdx != -1) {
            splitCandidates.add(subIdx)
        }
        if (Operation.MUL in splitFirst && mulIdx != -1) {
            splitCandidates.add(mulIdx)
        }
        return splitCandidates.maxOrNull() ?: listOf(addIdx, subIdx, mulIdx).maxOrNull()
        ?: error("No operations in $exprString")
    }

    fun parseExpression(string: String, splitFirst: List<Operation> = listOf()): Expression {
        val cleaned = string.filter { !it.isWhitespace() }
        val asLong = cleaned.toLongOrNull()
        return when {
            asLong != null -> Num(asLong)
            cleaned.last() == ')' -> {
                val matchingParenIdx = matchingParenIndex(cleaned)
                if (matchingParenIdx == 0) {
                    parseExpression(string.substring(1 until cleaned.length - 1), splitFirst)
                } else {
                    val splitIdx = splitIdx(cleaned.substring(0 until matchingParenIdx), splitFirst)
                    val leftExp = parseExpression(
                        cleaned.substring(0 until splitIdx),
                        splitFirst
                    ) // offset for op: leftExp*(rightExp)
                    val rightExp = parseExpression(cleaned.substring(splitIdx + 1 until cleaned.length), splitFirst)
                    val op = opFromChar(cleaned[splitIdx])
                    Compound(leftExp, rightExp, op)
                }
            }
            else -> {
                val splitIdx = splitIdx(cleaned, splitFirst)
                val leftExpr = parseExpression(cleaned.substring(0 until splitIdx), splitFirst)
                val rightExpr = parseExpression(cleaned.substring(splitIdx + 1 until cleaned.length), splitFirst)
                val op = opFromChar(cleaned[splitIdx])
                Compound(leftExpr, rightExpr, op)
            }
        }
    }
}
