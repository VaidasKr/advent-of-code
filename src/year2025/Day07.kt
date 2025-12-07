package year2025

import println
import readInput

fun main() {
    fun part1(inputs: List<String>): Long {
        var splits = 0L
        val first = inputs.first()
        var beams = setOf(first.indexOf('S'))
        var index = 1
        while (index < inputs.size) {
            val line = inputs[index]
            beams = buildSet {
                beams.forEach { beam ->
                    if (line[beam] == '^') {
                        splits++
                        add(beam - 1)
                        add(beam + 1)
                    } else {
                        add(beam)
                    }
                }
            }
            index++
        }
        return splits
    }

    fun part2(inputs: List<String>): Long {
        val memory = hashMapOf<Int, Long>()
        val first = inputs.first()
        val width = first.length

        fun calcPaths(x: Int, y: Int): Long {
            if (y >= inputs.size) return 1
            return if (inputs[y][x] == '^') {
                memory.getOrPut(x + y * width) { calcPaths(x - 1, y) + calcPaths(x + 1, y) }
            } else {
                calcPaths(x, y + 1)
            }
        }

        return calcPaths(first.indexOf('S'), 1)
    }

    val testInput = """
        .......S.......
        ...............
        .......^.......
        ...............
        ......^.^......
        ...............
        .....^.^.^.....
        ...............
        ....^.^...^....
        ...............
        ...^.^...^.^...
        ...............
        ..^...^.....^..
        ...............
        .^.^.^.^.^...^.
        ...............
    """.trimIndent()
        .lines()

    val actualInput = readInput(2025, 7)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
