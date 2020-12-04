package sh.zachwal.advent2020

fun main() {
    println(Day04().countValidPassports(input(4, 1).split(Regex("\\n\\n"))))

}

fun validInRange(str: String, range: IntRange): Boolean = str.toIntOrNull()?.let { range.contains(it) } ?: false

fun validHeight(str: String): Boolean {
    return when {
        str.endsWith("cm") -> validInRange(str.removeSuffix("cm"), (150..193))
        str.endsWith("in") -> validInRange(str.removeSuffix("in"), (59..76))
        else -> false
    }
}

class Day04 {
    enum class PassportFieldKey(val keyCode: String) {
        BYR("byr"),
        IYR("iyr"),
        EYR("eyr"),
        HGT("hgt"),
        HCL("hcl"),
        ECL("ecl"),
        PID("pid"),
        CID("cid");

        fun validValue(value: String): Boolean = when (this) {
            BYR -> validInRange(value, (1920..2002))
            IYR -> validInRange(value, (2010..2020))
            EYR -> validInRange(value, (2020..2030))
            HGT -> validHeight(value)
            HCL -> value.matches("#[0-9a-z]{6}".toRegex())
            ECL -> value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
            PID -> value.matches("[0-9]{9}".toRegex())
            CID -> true
        }
    }

    private companion object {
        val REQUIRED_KEYS: Set<PassportFieldKey>
            get() = setOf(
                PassportFieldKey.BYR,
                PassportFieldKey.IYR,
                PassportFieldKey.EYR,
                PassportFieldKey.HGT,
                PassportFieldKey.HCL,
                PassportFieldKey.ECL,
                PassportFieldKey.PID
            )
    }

    data class Passport(val fields: Map<PassportFieldKey, String>) {
        fun isValid(): Boolean = fields.keys.containsAll(REQUIRED_KEYS) &&
                fields.all { it.key.validValue(it.value) }
    }

    fun passport(str: String): Passport {
        val fieldRegex = Regex("(.+):(.+)")
        val fields = str.split("\\s".toRegex()).filter { it != "" }.associate {
            val match = fieldRegex.matchEntire(it)?.groupValues ?: error("Couldn't match $it")
            PassportFieldKey.valueOf(match[1].toUpperCase()) to match[2]
        }
        return Passport(fields)
    }

    fun countValidPassports(inputs: List<String>): Int = inputs.map(this::passport).count { it.isValid() }
}
