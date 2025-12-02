package year2025

import println
import readInput

private fun Long.isRepeating1(): Boolean {
    val string = toString()
    if (string.length % 2 != 0) return false
    val half = string.length / 2
    for (i in 0 until half) {
        if (string[i] != string[half + i]) return false
    }
    return true
}

private fun Long.isRepeating2(): Boolean {
    val string = toString()
    if (string.length == 1) return false

    val half = string.length / 2

    for (i in 1..half) {
        if (string.hasRepeating(i)) return true
    }

    return false
}

private fun String.hasRepeating(repLength: Int): Boolean {
    if (length % repLength != 0) {
        return false
    }
    val comps = length / repLength
    for (i in 0 until repLength) {
        val char = get(i)
        for (j in 1 until comps) {
            if (char != get(i + j * repLength)) return false
        }
    }
    return true
}

private inline fun solve(inputs: List<String>, isRepeating: (Long) -> Boolean): Long {
    var sum = 0L
    inputs.forEach { line ->
        val (start, end) = line.split('-')
        for (i in start.toLong()..end.toLong()) {
            if (isRepeating(i)) {
                sum += i
            }
        }
    }
    return sum
}

fun main() {
    fun part1(inputs: List<String>): Long = solve(inputs) { it.isRepeating1() }

    fun part2(inputs: List<String>): Long = solve(inputs) { it.isRepeating2() }

    val testInput = ("11-22,95-115,998-1012,1188511880-1188511890," +
            "222220-222224,1698522-1698528,446443-446449,38593856-38593862," +
            "565653-565659,824824821-824824827,2121212118-2121212124").split(',')

    val actualInput = readInput(2025, 2).first().split(',')

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
