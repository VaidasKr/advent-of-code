package year2024

import println
import readInput
import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Long {
        val lefts = mutableListOf<Long>()
        val rights = mutableListOf<Long>()
        input.forEach { line ->
            if (line.isNotBlank()) {
                val (left, right) = line.split("   ")
                lefts.add(left.toLong())
                rights.add(right.toLong())
            }
        }
        lefts.sort()
        rights.sort()
        var sum = 0L
        for (i in lefts.indices) {
            sum += (lefts[i] - rights[i]).absoluteValue
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val lefts = mutableListOf<Long>()
        val rights = mutableListOf<Long>()
        input.forEach { line ->
            if (line.isNotBlank()) {
                val (left, right) = line.split("   ")
                lefts.add(left.toLong())
                rights.add(right.toLong())
            }
        }
        var sum = 0L
        for (l in lefts.indices) {
            val left = lefts[l]
            var count = 0
            for (r in rights.indices) {
                if (left == rights[r]) count++
            }
            sum += left * count
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day2024-01-test")
    check(part1(testInput) == 11L)
    check(part2(testInput) == 31L)

    val input = readInput(2024, 1)
    part1(input).println()
    part2(input).println()
}
