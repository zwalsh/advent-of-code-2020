package sh.zachwal.advent2020

fun main() {
    val day07 = Day07()
    val part1 = input(7, 1).lines().filter { it != "" }
    println(day07.roots(part1, "shiny gold"))
    println(day07.totalCapacity(part1, "shiny gold"))
}

class Day07 {
    data class BagRule(val bag: String, val contains: Map<String, Int>)

    private val rootBagMatcher = Regex("(.*) bags contain")
    private val bagRuleMatcher = Regex("(\\d+) (.+?) bag")

    fun parseBagRule(rule: String): BagRule {
        val match = rootBagMatcher.find(rule) ?: error("Could not match root bag in $rule")
        val rootBag = match.groupValues[1]

        val bagRuleMatches = bagRuleMatcher.findAll(rule.substringAfter("contain"))
        val containsMap = bagRuleMatches.map { it.groupValues }.associate { it[2] to it[1].toInt() }
        return BagRule(rootBag, containsMap)
    }

    fun roots(rules: List<String>, bag: String): Int {
        val bagRules = rules.map(this::parseBagRule)
        val parentBags = bagRules.flatMap {
            it.contains.keys.map { containedBag -> containedBag to it.bag }
        }.groupBy({ it.first }, { it.second })
        val rootBagsForTargetBag = mutableSetOf<String>()
        val nextBagList = mutableListOf(bag)
        while (nextBagList.isNotEmpty()) {
            val nextBag = nextBagList.removeFirst()
            if (nextBag in rootBagsForTargetBag) {
                continue
            }
            rootBagsForTargetBag.add(nextBag)
            nextBagList.addAll(parentBags.getOrDefault(nextBag, emptyList()))
        }
        return rootBagsForTargetBag.size - 1 // for original bag color
    }

    fun totalCapacity(rules: List<String>, bag: String): Int {
        val bagRules = rules.map(this::parseBagRule)
        val bagRuleMap = bagRules.associateBy { it.bag }
        return capacity(bag, bagRuleMap)
    }

    private fun capacity(bag: String, rules: Map<String, BagRule>): Int {
        val childBags = rules[bag]?.contains?.keys ?: emptySet()
        return childBags.sumBy { child ->
            val capacity = capacity(child, rules)
            val countInBag = rules[bag]?.contains?.get(child) ?: 0
            (capacity + 1) * countInBag // add 1 for the child bag itself
        }
    }
}
