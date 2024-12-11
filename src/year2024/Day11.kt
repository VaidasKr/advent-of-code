package year2024

import println
import readInput
import kotlin.time.measureTime

fun main() {
    fun blink(rocks: List<String>): List<String> = buildList(rocks.size) {
        for (i in rocks.indices) {
            rocks[i].blink { add(it) }
        }
    }

    fun part1(inputs: List<String>): Int {
        var rocks = inputs.first().split(' ')

        repeat(75) {
            rocks = blink(rocks)
        }

        return rocks.size
    }

    fun part2(inputs: List<String>, blinks: Int): Long {
        var count = 0L
        val rocks = inputs.first().split(' ').map { it to blinks }.toMutableList()

        while (rocks.isNotEmpty()) {
            val (rock, blinksLeft) = rocks.removeAt(0)
            var rockValue = rock
            repeat(blinksLeft) {
                if (rockValue == "0") {
                    rockValue = "1"
                } else {
                    if (rockValue.length % 2 == 0) {
                        rocks.add(0, rockValue.substring(rockValue.length / 2).removeLeadingZeros() to blinksLeft - it - 1)
                        rockValue = rockValue.substring(0, rockValue.length / 2)
                    } else {
                        rockValue = rockValue.toLong().times(2024).toString()
                    }
                }
            }
            count++
            println("done $count left ${rocks.size}")
        }

        return count
    }

    val testInput = listOf("125 17")
    val actualInput = readInput(2024, 11)

    part2(testInput, 6).println()
    measureTime {
        part2(actualInput, 75).println()
    }.println()

//    part1(testInput).println()
//    part1(actualInput).println()
//    part2(actualInput).println()
}

private inline fun String.blink(next: (String) -> Unit) {
    if (this == "0") {
        next("1")
    } else {
        if (length % 2 == 0) {
            next(substring(0, length / 2))
            next(substring(length / 2).removeLeadingZeros())
        } else {
            next(toLong().times(2024).toString())
        }
    }
}

private inline fun String.blink(next: (String) -> Unit, split: (String, String) -> Unit) {
    if (this == "0") {
        next("1")
    } else {
        if (length % 2 == 0) {
            split(substring(0, length / 2), substring(length / 2).removeLeadingZeros())
        } else {
            next(toLong().times(2024).toString())
        }
    }
}

private fun String.removeLeadingZeros(): String = if (startsWith('0') && length > 1) {
    val index = indexOfFirst { it != '0' }
    if (index == -1) {
        "0"
    } else {
        substring(index)
    }
} else {
    this
}
