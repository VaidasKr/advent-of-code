package year2024

import println
import readInput

fun main() {
    fun part1(inputs: List<String>): Long {
        val blankIndex = inputs.indexOfFirst { it.isBlank() }

        val rules = HashMap<String, ArrayList<String>>()

        for (i in 0 until blankIndex) {
            val (x, y) = inputs[i].split('|')
            rules.getOrPut(x) { ArrayList() }.add(y)
        }

        var sum = 0L

        for (i in blankIndex + 1 until inputs.size) {
            val line = inputs[i]
            if (line.isBlank()) break
            val numbersBefore = HashSet<String>()
            val numbers = line.split(',')
            if (numbers.all { number ->
                    val mustBeAfter = rules[number].orEmpty()
                    val result = mustBeAfter.none { it in numbersBefore }
                    numbersBefore.add(number)
                    result
                }
            ) {
                sum += numbers[numbers.size / 2].toLong()
            }
        }

        return sum
    }

    fun part2(inputs: List<String>): Long {
        val blankIndex = inputs.indexOfFirst { it.isBlank() }

        val rules = HashMap<String, ArrayList<String>>()

        for (i in 0 until blankIndex) {
            val (x, y) = inputs[i].split('|')
            rules.getOrPut(x) { ArrayList() }.add(y)
        }

        var sum = 0L

        for (i in blankIndex + 1 until inputs.size) {
            val line = inputs[i]
            if (line.isBlank()) break
            val numbersBefore = HashSet<String>()
            val numbers = line.split(',')
            if (!numbers.all { number ->
                    val mustBeAfter = rules[number].orEmpty()
                    val result = mustBeAfter.none { it in numbersBefore }
                    numbersBefore.add(number)
                    result
                }) {
                val resort = numbers.sortedWith { first, last ->
                    if (rules[first].orEmpty().contains(last)) {
                        -1
                    } else if (rules[last].orEmpty().contains(first)) {
                        1
                    } else {
                        0
                    }
                }
                sum += resort[numbers.size / 2].toLong()
            }
        }

        return sum
    }

    val testInput = readInput("Day2024-05-test")
    val actualInput = readInput(2024, 5)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
