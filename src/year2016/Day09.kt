package year2016

import printCompare
import println
import readInput

fun main() {
    "ADVENT".solve1().println()
    "A(1x5)BC".solve1().println()
    "(3x3)XYZ".solve1().println()
    "A(2x2)BCD(2x2)EFG".solve1().println()
    "(6x1)(1x3)A".solve1().println()
    "X(8x2)(3x3)ABCY".solve1().println()

    val input = readInput(2016, 9).first()

    input.solve1().printCompare(99145)

    "(3x3)XYZ".solve2().printCompare(9)
    "X(8x2)(3x3)ABCY".solve2().printCompare("XABCABCABCABCABCABCY".solve1())
    "(27x12)(20x12)(13x14)(7x10)(1x12)A".solve2().printCompare(241920)
    "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN".solve2().printCompare(445)

    input.solve2().println()
}

private fun String.solve1(): Int {
    var count = 0
    var start = 0
    var sep = indexOf('(')
    while (sep != -1) {
        count += sep - start
        val end = indexOf(')', sep)
        val substring = substring(sep + 1, end)
        val (chars, repeat) = substring.split('x')
        count += repeat.toInt() * chars.toInt()
        start = end + 1 + chars.toInt()
        sep = indexOf('(', start)
    }
    count += length - start
    return count
}

private fun String.solve2(): Long {
    var count = 0L
    var start = 0
    var sep = indexOf('(')
    while (sep != -1) {
        count += sep - start
        val x = indexOf('x', sep)
        val chars = substring(sep + 1, x).toInt()
        val end = indexOf(')', sep)
        val repeat = substring(x + 1, end).toInt()
        count += repeat * substring(end + 1, end + 1 + chars).solve2()
        start = end + 1 + chars
        sep = indexOf('(', start)
    }
    count += length - start
    return count
}
