package year2024

import println
import readInput
import readTestInput

fun String.getXYAfter(after: Char): Pair<Int, Int> {
    val sep = indexOf(',')
    return substring(indexOf(after) + 1, sep).toInt() to substring(indexOf(after, sep) + 1).toInt()
}

inline fun matchingButtonPair(
    maxA: Int,
    maxB: Int,
    a: Int,
    b: Int,
    prize: Int,
    onMatch: (Int, Int) -> Unit
) {
    val maxADestination = a * maxA
    val maxBDestination = b * maxB
    if (maxADestination + maxBDestination < prize) return
    val minA = ((prize - maxBDestination) / a.toFloat()).toInt()
    for (aI in minA..maxA) {
        val aDestination = aI * a
        if (aDestination > prize) return
        if (aDestination == prize) {
            onMatch(aI, 0)
            return
        }
        val left = prize - aDestination
        if (left <= maxBDestination && left % b == 0) {
            onMatch(aI, left / b)
        }
    }
}

fun main() {
    fun calcMinSum(a: Pair<Int, Int>, b: Pair<Int, Int>, prize: Pair<Int, Int>): Int {
        var sum = -1
        matchingButtonPair(100, 100, a.first, b.first, prize.first) { aPress, bPress ->
            if (aPress * a.second + bPress * b.second == prize.second) {
                val price = aPress * 3 + bPress
                if (sum == -1 || sum > price) {
                    sum = price
                }
            }
        }
        return if (sum == -1) 0 else sum
    }

    fun part1(inputs: List<String>): Long {
        var sum = 0L
        for (i in inputs.indices step 4) {
            sum += calcMinSum(inputs[i].getXYAfter('+'), inputs[i + 1].getXYAfter('+'), inputs[i + 2].getXYAfter('='))
        }
        return sum
    }

    fun part2(inputs: List<String>, movePrize: Long): Long {
        var sum = 0L
        for (i in inputs.indices step 4) {
            val (prizeX, prizeY) = inputs[i + 2].getXYAfter('=')

            val (aX, aY) = inputs[i].getXYAfter('+')
            val (bX, bY) = inputs[i + 1].getXYAfter('+')
            val (pX, pY) = prizeX + movePrize to prizeY + movePrize

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

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput, 10000000000000).println()
    part2(actualInput, 10000000000000).println()
}
