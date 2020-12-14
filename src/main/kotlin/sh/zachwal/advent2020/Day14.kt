package sh.zachwal.advent2020

class Day14 {

    fun applyBitmask(mask: String, num: Long): Long {
        val asBinary = num.toString(2)
        val paddedBinary = asBinary.padStart(mask.length, '0')
        return mask.toCharArray().foldIndexed(paddedBinary) { index, acc, charMask ->
            when (charMask) {
                '0', '1' -> {
                    val chars = acc.toCharArray()
                    chars[index] = charMask
                    String(chars)
                }
                else -> acc
            }
        }.toLong(2)
    }
}
