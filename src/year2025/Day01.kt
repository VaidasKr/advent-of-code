package year2025

import printCompare
import println
import readInput
import kotlin.math.absoluteValue

fun main() {
    fun part1(inputs: List<String>): Long {
        var start = 50
        var timesAtZero = 0L
        inputs.forEach { line ->
            val amount = if (line.startsWith("L")) {
                -line.substring(1).toInt()
            } else if (line.startsWith("R")) {
                line.substring(1).toInt()
            } else {
                error("Unsupported")
            }
            start += amount
            start = start.mod(100)
            if (start == 0) {
                timesAtZero++
            }
        }
        return timesAtZero
    }

    fun part2(inputs: List<String>): Long {
        var start = 50L
        var timesPastZero = 0L
        inputs.forEach { line ->
            val amount = if (line.startsWith("L")) {
                -line.substring(1).toInt()
            } else if (line.startsWith("R")) {
                line.substring(1).toInt()
            } else {
                error("Unsupported")
            }
            val next = start + amount
            if (next <= 0) {
                val reduce = if (start == 0L) 0 else 1
                timesPastZero += reduce + next.absoluteValue / 100
            } else if (next >= 100) {
                timesPastZero += next / 100
            }
            start = next.mod(100L)
        }
        return timesPastZero
    }

    val testInput = """L68
L30
R48
L5
R60
L55
L1
L99
R14
L82""".lines()

    val actualInput = readInput(2025, 1)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
