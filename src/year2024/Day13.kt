package year2024

import println
import readInput
import readTestInput

fun String.getXYAfter(after: Char): Pair<Int, Int> {
    val sep = indexOf(',')
    return substring(indexOf(after) + 1, sep).toInt() to substring(indexOf(after, sep) + 1).toInt()
}

fun main() {
    fun part2(inputs: List<String>, movePrize: Long): Long {
        var sum = 0L
        for (i in inputs.indices step 4) {
            val (aX, aY) = inputs[i].getXYAfter('+')

            val (bX, bY) = inputs[i + 1].getXYAfter('+')

            val (prizeX, prizeY) = inputs[i + 2].getXYAfter('=')
            val pX = prizeX + movePrize
            val pY = prizeY + movePrize

            val b = (pY * aX - pX * aY) / (bY * aX - bX * aY);
            val a = (pX - b * bX) / aX;

            if (a * aX + b * bX == pX && a * aY + b * bY == pY) {
                sum += a * 3 + b
            }
        }
        return sum
    }

    val testInput = readTestInput(2024, 13)
    val actualInput = readInput(2024, 13)

    part2(testInput, 0).println()
    part2(actualInput, 0).println()

    part2(actualInput, 10000000000000).println()
}
