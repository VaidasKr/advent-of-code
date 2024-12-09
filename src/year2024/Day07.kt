package year2024

import println
import readInput
import readTestInput

fun main() {
    fun hasSum(expected: Long, numbers: LongArray, index: Int, sum: Long): Boolean {
        if (index >= numbers.size) {
            return sum == expected
        }
        val num = numbers[index]
        return hasSum(expected, numbers, index + 1, sum + num) || hasSum(expected, numbers, index + 1, sum * num)
    }

    fun part1(inputs: List<String>): Long {
        var sum = 0L
        for (i in inputs.indices) {
            val line = inputs[i]
            if (line.isBlank()) break
            val (result, values) = line.split(':')
            val numberStrings = values.trim().split(' ')
            val expected = result.toLong()
            if (hasSum(expected, LongArray(numberStrings.size) { numberStrings[it].toLong() }, 0, 0)) {
                sum += expected
            }
        }
        return sum
    }

    fun concat(first: Long, second: Long): Long {
        var result = first * 10
        var left = second
        while (left / 10 > 0) {
            left /= 10
            result *= 10
        }
        return result + second
    }

    fun hasSum2(expected: Long, numbers: LongArray, index: Int, sum: Long): Boolean {
        if (index >= numbers.size) {
            return sum == expected
        }
        val num = numbers[index]
        return hasSum2(expected, numbers, index + 1, sum + num) ||
                hasSum2(expected, numbers, index + 1, sum * num) ||
                hasSum2(expected, numbers, index + 1, concat(sum, num))
    }

    fun part2(inputs: List<String>): Long {
        var sum = 0L
        for (i in inputs.indices) {
            val line = inputs[i]
            if (line.isBlank()) break
            val (result, values) = line.split(':')
            val numberStrings = values.trim().split(' ')
            val expected = result.toLong()
            val numbers = LongArray(numberStrings.size) { numberStrings[it].toLong() }
            if (hasSum2(expected, numbers, 1, numbers.first())) {
                sum += expected
            }
        }
        return sum
    }

    val testInput = readTestInput(2024, 7)
    val actualInput = readInput(2024, 7)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
