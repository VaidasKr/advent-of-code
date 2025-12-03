package year2025

import println
import readInput

private fun String.maxVoltage(batteries: Int): Long {
    val volts = LongArray(batteries) { 0 }
    for (i in indices) {
        val value = get(i).digitToInt().toLong()
        for (j in volts.indices) {
            if (value > volts[j] && lastIndex - i > volts.lastIndex - j - 1) {
                volts[j] = value
                for (k in j + 1 until volts.size) {
                    volts[k] = 0
                }
                break
            }
        }
    }
    var sum = 0L
    for (i in volts.indices) {
        sum = sum * 10 + volts[i]
    }
    return sum
}

private fun solve(inputs: List<String>, batteries: Int): Long {
    var sum = 0L
    for (i in inputs.indices) {
        sum += inputs[i].maxVoltage(batteries)
    }
    return sum
}

fun main() {
    fun part1(inputs: List<String>) = solve(inputs, 2)

    fun part2(inputs: List<String>) = solve(inputs, 12)

    val testInput = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.trimIndent()
        .lines()
    val actualInput = readInput(2025, 3)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
