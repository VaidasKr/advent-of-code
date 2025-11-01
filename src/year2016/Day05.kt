package year2016

import md5
import println
import readInput
import readTestInput
import kotlin.time.measureTime

fun main() {
    fun part1(inputs: List<String>): String = buildString {
        val line = inputs.first()
        var number = 0
        while (length < 8) {
            val md5 = (line + number++).md5()
            if (md5.startsWith("00000")) {
                append(md5[5])
            }
        }
    }

    fun part2(inputs: List<String>): String {
        val sequence = CharArray(8)

        var added = 0
        val line = inputs.first()
        var number = 0
        while (added < 8) {
            val md5 = (line + number++).md5()
            if (md5.startsWith("00000")) {
                val position = md5[5]
                if (position.isDigit()) {
                    val digit = position.digitToInt()
                    if (digit < 8 && sequence[digit] == '\u0000') {
                        sequence[digit] = md5[6]
                        added++
                    }
                }
            }
        }

        return String(sequence)
    }

    val testInput = readTestInput(2016, 5)
    val actualInput = readInput(2016, 5)

    measureTime { part1(testInput).println() }.println()
    measureTime { part1(actualInput).println() }.println()

    measureTime { part2(testInput).println() }.println()
    measureTime { part2(actualInput).println() }.println()
}
