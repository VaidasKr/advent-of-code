package year2024

import println
import readInput
import readTestInput

fun main() {
    fun isPossible(design: String, match: String, patterns: List<String>, cache: HashMap<String, Boolean>): Boolean {
        if (design == match) return true
        return if (design.startsWith(match)) {
            cache.getOrPut(design.substring(match.length)) {
                patterns.any { isPossible(design, match + it, patterns, cache) }
            }
        } else {
            false
        }
    }

    fun part1(inputs: List<String>): Int {
        val patterns = inputs[0].split(", ")
        var sum = 0
        val cache = hashMapOf<String, Boolean>()
        for (i in 2 until inputs.size) {
            if (isPossible(inputs[i], "", patterns, cache)) {
                sum++
            }
        }
        return sum
    }

    fun possibilities(design: String, match: String, patterns: List<String>, cache: HashMap<String, Long>): Long {
        if (design == match) return 1
        return if (design.startsWith(match)) {
            cache.getOrPut(design.substring(match.length)) {
                patterns.sumOf { possibilities(design, match + it, patterns, cache) }
            }
        } else {
            0L
        }
    }

    fun part2(inputs: List<String>): Long {
        val patterns = inputs[0].split(", ")
        val cache = hashMapOf<String, Long>()
        var sum = 0L
        for (i in 2 until inputs.size) {
            val design = inputs[i]
            val possibilities = possibilities(design, "", patterns, cache)
            sum += possibilities
        }
        return sum
    }

    val testInput = readTestInput(2024, 19)
    val actualInput = readInput(2024, 19)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
