package year2025

import println
import readInput

private fun countFreshProducts(inputs: List<String>, from: Int, ranges: List<LongRange>): Long {
    var freshCount = 0L

    for (i in from until inputs.size) {
        val value = inputs[i].toLong()
        if (ranges.any { value in it }) {
            freshCount++
        }
    }

    return freshCount
}

fun main() {
    fun part1(inputs: List<String>): Long {
        val ranges = mutableListOf<LongRange>()

        for (i in inputs.indices) {
            val line = inputs[i]
            if (line.isBlank()) {
                return countFreshProducts(inputs, i + 1, ranges)
            } else {
                val split = line.indexOf('-')
                val from = line.take(split).toLong()
                val to = line.substring(split + 1).toLong()
                ranges += from..to
            }
        }

        return 0
    }

    fun part2(inputs: List<String>): Long {
        var ranges = emptyList<LongRange>()

        lines@ for (i in inputs.indices) {
            val line = inputs[i]
            if (line.isBlank()) {
                return ranges.sumOf { it.last - it.first + 1 }
            } else {
                val split = line.indexOf('-')
                var from = line.take(split).toLong()
                var to = line.substring(split + 1).toLong()

                val newRanges = buildList {
                    for (r in ranges.indices) {
                        val range = ranges[r]
                        val otherFrom = range.first
                        val otherTo = range.last
                        if (from >= otherFrom && to <= otherTo) continue@lines
                        if (from < otherFrom && to > otherTo) continue
                        if (to < otherFrom || from > otherTo) {
                            add(range)
                            continue
                        }

                        if (from < otherFrom) {
                            to = otherTo
                        } else {
                            from = otherFrom
                        }
                    }
                    add(from..to)
                }
                ranges = newRanges
            }
        }

        return 0
    }

    val testInput = """
        3-5
        10-14
        16-20
        12-18

        1
        5
        8
        11
        17
        32
    """.trimIndent()
        .lines()


    val actualInput = readInput(2025, 5)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
