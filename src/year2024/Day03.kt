package year2024

import println
import readInput
import java.util.concurrent.atomic.AtomicBoolean

fun main() {
    fun sumOfLine(line: String): Long {
        var sum = 0L
        var startIndex = line.indexOf("mul(", 0)
        while (startIndex != -1) {
            val firstNumIndex = startIndex + 4
            val separator = line.indexOf(",", firstNumIndex)
            if (separator == -1) break
            val endIndex = line.indexOf(")", separator + 1)
            if (endIndex == -1) break

            val i = separator - firstNumIndex
            val i1 = endIndex - separator
            if (i in 1..3 && i1 in 2..4) {
                val firstNumString = line.substring(firstNumIndex, separator)
                val lastNumString = line.substring(separator + 1, endIndex)

                val firstNum = firstNumString.toLongOrNull()
                val lastNum = lastNumString.toLongOrNull()
                if (firstNum != null && lastNum != null) {
                    sum += firstNum * lastNum
                }
            }

            startIndex = line.indexOf("mul(", firstNumIndex + 1)
        }
        return sum
    }

    fun part1(inputs: List<String>): Long = inputs.sumOf { sumOfLine(it) }

    fun updateLine(line: String, enabled: AtomicBoolean): String = buildString {
        var index = 0
        var disableIndex: Int = if (enabled.get()) {
            line.indexOf("don't()")
        } else {
            0
        }
        while (disableIndex != -1) {
            append(line.substring(index, disableIndex))
            index = line.indexOf("do()", disableIndex)
            if (index == -1) {
                break
            }
            disableIndex = line.indexOf("don't()", index)
        }
        if (disableIndex == -1) {
            enabled.set(true)
            append(line.substring(index))
        } else {
            enabled.set(false)
        }
    }

    fun part2(inputs: List<String>): Long {
        val enabled = AtomicBoolean(true)
        return inputs.sumOf { sumOfLine(updateLine(it, enabled)) }
    }

    part1(readInput("Day2024-03-test")).println()
    part2(readInput("Day2024-03-test2")).println()

    val readInput = readInput(2024, 3)

    part1(readInput).println()
    part2(readInput).println()
}
