package year2025

import println
import readInput

private inline fun String.parts(onPart: (Int, String) -> Unit) {
    val line = this
    var index = line.nonWhiteSpaceIndex(0)
    var partIndex = 0
    while (index != -1) {
        val whiteSpace = line.whiteSpaceIndex(index).let { if (it == -1) line.length else it }
        onPart(partIndex++, substring(index, whiteSpace))
        index = line.nonWhiteSpaceIndex(whiteSpace)
    }
}

private fun String.nonWhiteSpaceIndex(from: Int = 0): Int {
    for (i in from until length) {
        if (!get(i).isWhitespace()) return i
    }
    return -1
}

private fun String.whiteSpaceIndex(from: Int = 0): Int {
    for (i in from until length) {
        if (get(i).isWhitespace()) return i
    }
    return -1
}

fun main() {
    fun part1(inputs: List<String>): Long {
        val collections = HashMap<Int, MutableList<Long>>()
        for (i in 0 until inputs.lastIndex) {
            inputs[i].parts { index, part ->
                collections.getOrPut(index) { mutableListOf() }.add(part.toLong())
            }
        }
        var sum = 0L
        inputs.last().parts { index, part ->
            when (part) {
                "+" -> sum += collections[index]!!.sum()
                "*" -> sum += collections[index]!!.fold(1L) { acc, lng -> acc * lng }
                else -> error("illegal operation")
            }
        }
        return sum
    }

    fun part2(inputs: List<String>): Long {
        var sum = 0L
        var index = 0
        val indices = inputs.indices

        var currentDigit = 0L
        val digits = ArrayList<Long>()
        var multiply: Boolean? = null

        out@ while (true) {
            var allWhitespace = true
            currentDigit = 0
            for (i in indices) {
                val char = inputs[i].getOrNull(index) ?: break@out
                when {
                    char == '*' -> {
                        allWhitespace = false
                        if (currentDigit != 0L) {
                            digits += currentDigit
                            index++
                            multiply = true
                            continue@out
                        }
                    }

                    char == '+' -> {
                        allWhitespace = false
                        if (currentDigit != 0L) {
                            digits += currentDigit
                            index++
                            multiply = false
                            continue@out
                        }
                    }

                    char.isDigit() -> {
                        allWhitespace = false
                        currentDigit = currentDigit * 10 + char.digitToInt()
                    }

                    char.isWhitespace() -> {
                        if (currentDigit != 0L) {
                            digits += currentDigit
                            val lastChar = inputs.last()[index++]
                            if (lastChar == '*') {
                                multiply = true
                            } else if (lastChar == '+') {
                                multiply = false
                            }
                            continue@out
                        }
                    }

                    else -> error("Illegal symbol")
                }
            }
            if (currentDigit != 0L) {
                digits += currentDigit
            }
            if (allWhitespace) {
                if (multiply!!) {
                    sum += digits.fold(1L) { acc, a -> acc * a }
                } else {
                    sum += digits.sum()
                }
                multiply = null
                digits.clear()
            }
            index++
        }
        if (multiply!!) {
            sum += digits.fold(1L) { acc, a -> acc * a }
        } else {
            sum += digits.sum()
        }
        return sum
    }

    val testInput = """
        123 328  51 64 
         45 64  387 23 
          6 98  215 314
        *   +   *   +  
    """.trimIndent()
        .lines()

    val actualInput = readInput(2025, 6)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
