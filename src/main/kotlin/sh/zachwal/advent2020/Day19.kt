package sh.zachwal.advent2020

fun main() {
    val day19 = Day19()
    val input = input(19, 2).split("\n\n")
    val rules = input[0].lines()
    val messages = input[1].lines().filter { it != "" }

    val rulebook = day19.ruleBook(rules)
    println(day19.matchingRules(rulebook, 0, messages))
}

/*
Rule:
- CharRule
- CompoundRule
- OrRule
 */

interface Rule {
    /*
    Returns the number of characters that this rule could match
     */
    fun match(input: String, ruleBook: Map<Int, Rule>): List<Int>
}

data class CharRule(val char: Char) : Rule {
    override fun match(input: String, ruleBook: Map<Int, Rule>): List<Int> {
        return if (input.getOrNull(0) == char) {
            listOf(1)
        } else {
            emptyList()
        }
    }
}

data class AndRule(
    val rules: List<Rule>
) : Rule {

    private fun extendMatch(input: String, soFar: Int, nextRule: Rule, ruleBook: Map<Int, Rule>): List<Int> {
        val unmatched = input.substring(soFar)
        val nextRuleMatch = nextRule.match(unmatched, ruleBook)
        return nextRuleMatch.map { it + soFar }
    }

    override fun match(input: String, ruleBook: Map<Int, Rule>): List<Int> {
        return rules.fold(listOf(0)) { soFar, nextRule ->
            soFar.flatMap { possMatch ->
                extendMatch(input, possMatch, nextRule, ruleBook)
            }
        }
    }
}

data class OrRule(
    val rules: List<Rule>
) : Rule {
    override fun match(input: String, ruleBook: Map<Int, Rule>): List<Int> {
        return rules.flatMap { it.match(input, ruleBook) }.filter { it != 0 }
    }
}

data class RuleReference(
    val ruleNum: Int
) : Rule {
    override fun match(input: String, ruleBook: Map<Int, Rule>): List<Int> {
        return ruleBook[ruleNum]?.match(input, ruleBook) ?: error("No rule in book at $ruleNum")
    }

}

class Day19 {

    private fun parseRule(rule: String): Rule {
        return when {
            '|' in rule -> OrRule(rule.split("|").filter { it != "" }.map(::parseRule))
            '"' in rule -> CharRule(rule.first { it != '"' })
            rule.toIntOrNull() == null -> AndRule(rule.split(" ").filter { it != "" }.map(::parseRule))
            else -> RuleReference(rule.toInt())
        }
    }

    fun parseRulePair(ruleString: String): Pair<Int, Rule> {
        val index = ruleString.substringBefore(":").toInt()
        val rule = ruleString.substringAfter(": ")
        return index to parseRule(rule)
    }

    fun ruleBook(ruleStrings: List<String>): Map<Int, Rule> {
        return ruleStrings.map(::parseRulePair).toMap()
    }

    fun matchingRules(ruleBook: Map<Int, Rule>, ruleNum: Int, messages: List<String>): Int {
        val rule = ruleBook[ruleNum] ?: error("No rule at num $ruleNum")
        return messages.count {
            val matchSize = rule.match(it, ruleBook)
            println("$matchSize/${it.length} of $it")
            it.length in matchSize
        }
    }
}
