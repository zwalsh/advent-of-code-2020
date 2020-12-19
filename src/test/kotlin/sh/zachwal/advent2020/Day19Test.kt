package sh.zachwal.advent2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day19Test {
    private val day19 = Day19()

    private val exampleRules = listOf(
        "0: 4 1 5",
        "1: 2 3 | 3 2",
        "2: 4 4 | 5 5",
        "3: 4 5 | 5 4",
        "4: \"a\"",
        "5: \"b\"",
    )

    private val exampleMessages = listOf(
        "ababbb",
        "bababa",
        "abbbab",
        "aaabbb",
        "aaaabbb",
    )

    @Test
    fun `parse compound rule from ruleset`() {
        assertEquals(
            0 to AndRule(
                listOf(
                    RuleReference(4),
                    RuleReference(1),
                    RuleReference(5)
                )
            ),
            day19.parseRulePair(exampleRules[0])
        )
    }

    private val charA = CharRule('a')
    private val charB = CharRule('b')

    @Test
    fun `parse char rule`() {
        assertEquals(4 to charA, day19.parseRulePair(exampleRules[4]))
    }

    @Test
    fun `parse or rule`() {
        assertEquals(
            1 to OrRule(
                listOf(
                    AndRule(listOf(RuleReference(2), RuleReference(3))),
                    AndRule(listOf(RuleReference(3), RuleReference(2))),
                )
            ),
            day19.parseRulePair(exampleRules[1])
        )
    }

    @Test
    fun `match char rule`() {
        assertEquals(1, charA.match("aaaaa", emptyMap()))
        assertEquals(0, charA.match("b", emptyMap()))
    }

    @Test
    fun `match and rule`() {
        assertEquals(2, AndRule(listOf(charA, charA)).match("aaaa", emptyMap()))
        assertEquals(0, AndRule(listOf(charA, charA)).match("baaa", emptyMap()))
        assertEquals(0, AndRule(listOf(charA, charA, charA)).match("aba", emptyMap()))
    }

    @Test
    fun `match or rule`() {
        assertEquals(1, OrRule(listOf(charA, charB)).match("ab", emptyMap()))
        assertEquals(1, OrRule(listOf(charA, charB)).match("ba", emptyMap()))
        assertEquals(0, OrRule(listOf(charA, charB)).match("cab", emptyMap()))
    }

    @Test
    fun `match ref rule`() {
        assertEquals(1, RuleReference(0).match("a", mapOf(0 to charA)))
    }

    @Test
    fun `can count matching rules`() {
        assertEquals(2, day19.matchingRules(day19.ruleBook(exampleRules), 0, exampleMessages))
    }
}
