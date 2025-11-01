package year2016

import println
import readInput
import readTestInput

private inline fun solve(lines: List<String>, pick: Map<Char, Int>.() -> Char): String =
    buildString {
        val first = lines.first()
        for (x in first.indices) {
            val memory = hashMapOf<Char, Int>()
            for (y in lines.indices) {
                val char = lines[y][x]
                memory[char] = 1 + memory.getOrElse(char) { 0 }
            }
            append(memory.pick())
        }
    }


fun main() {
    fun part1(lines: List<String>): String = solve(lines) { maxBy { it.value }.key }
    fun part2(lines: List<String>): String = solve(lines) { minBy { it.value }.key }
    
    val testInput = readTestInput(2016, 6)
    val actualInput = readInput(2016, 6)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
