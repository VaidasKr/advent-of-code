package year2024

import println
import readInput

fun main() {
    fun testLine(line: String): Boolean {
        var previous = 0
        var increasing = false
        val numbers = line.split(' ')
        for (i in numbers.indices) {
            val number = numbers[i].toInt()
            if (i == 0) {
                // skip
            } else {
                if (i == 1) {
                    increasing = number > previous
                }
                val dif = number - previous
                val range = if (increasing) 1..3 else -3..-1
                if (dif !in range) {
                    return false
                }
            }
            previous = number
        }
        return true
    }

    fun part1(inputs: List<String>): Int = inputs.count { it.isNotBlank() && testLine(it) }

    fun part2(inputs: List<String>): Int = inputs.count { line ->
        if (line.isBlank()) {
            false
        } else if (testLine(line)) {
            true
        } else {
            val numbers = line.split(' ')
            var result = false
            for (i in numbers.indices) {
                if (testLine(numbers.toMutableList().apply { removeAt(i) }.joinToString(" "))) {
                    result = true
                    break
                }
            }
            result
        }
    }

    val testInput = readInput("Day2024-02-test")
    val input = readInput(2024, 2)

    println("part 1")

    part1(testInput).println()
    part1(input).println()

    println("part 2")

    part2(testInput).println()
    part2(input).println()
}
