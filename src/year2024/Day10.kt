package year2024

import println
import readInput
import readTestInput

fun main() {
    fun getTrailHeadScore(inputs: List<String>, width: Int, x: Int, y: Int): Int {
        val endPositions = hashSetOf<Int>()
        var ongoing = hashSetOf(x + y * width)

        while (ongoing.isNotEmpty()) {
            val next = hashSetOf<Int>()
            ongoing.forEach { combined ->
                val posX = combined % width
                val posY = combined / width
                val value = inputs[posY][posX].digitToInt()
                if (value == 9) {
                    endPositions.add(combined)
                } else {
                    val nextVal = (value + 1).digitToChar()
                    val leftX = posX - 1
                    val rightX = posX + 1
                    val topY = posY - 1
                    val bottomY = posY + 1
                    if (leftX in 0..<width && inputs[posY][leftX] == nextVal) {
                        next.add(leftX + posY * width)
                    }
                    if (rightX in 0..<width && inputs[posY][rightX] == nextVal) {
                        next.add(rightX + posY * width)
                    }
                    if (topY in inputs.indices && posX in inputs[topY].indices && inputs[topY][posX] == nextVal) {
                        next.add(posX + topY * width)
                    }
                    if (bottomY in inputs.indices && posX in inputs[bottomY].indices && inputs[bottomY][posX] == nextVal) {
                        next.add(posX + bottomY * width)
                    }
                }
            }
            ongoing = next
        }
        return endPositions.size
    }

    fun part1(inputs: List<String>): Long {
        var sum = 0L
        val width = inputs[0].length
        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                val char = line[x]
                if (char.digitToInt() == 0) {
                    sum += getTrailHeadScore(inputs, width, x, y)
                }
            }
        }
        return sum
    }

    fun getTrainHeadRating(inputs: List<String>, x: Int, y: Int, value: Int): Int {
        if (value == 9) return 1
        var sum = 0
        val nextValue = value + 1
        val nextChar = nextValue.digitToChar()
        if (y + 1 in inputs.indices && x in inputs[y + 1].indices && inputs[y + 1][x] == nextChar) {
            sum += getTrainHeadRating(inputs, x, y + 1, nextValue)
        }
        if (y - 1 in inputs.indices && x in inputs[y - 1].indices && inputs[y - 1][x] == nextChar) {
            sum += getTrainHeadRating(inputs, x, y - 1, nextValue)
        }
        if (y in inputs.indices) {
            val yLine = inputs[y]
            val indices = yLine.indices
            if (x + 1 in indices && yLine[x + 1] == nextChar) {
                sum += getTrainHeadRating(inputs, x + 1, y, nextValue)
            }
            if (x - 1 in indices && yLine[x - 1] == nextChar) {
                sum += getTrainHeadRating(inputs, x - 1, y, nextValue)
            }
        }
        return sum
    }

    fun part2(inputs: List<String>): Long {
        var sum = 0L
        val width = inputs[0].length
        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                val char = line[x]
                if (char.digitToInt() == 0) {
                    sum += getTrainHeadRating(inputs, x, y, 0)
                }
            }
        }
        return sum
    }

    val testInput = readTestInput(2024, 10)
    val actualInput = readInput(2024, 10)

    part1(testInput).println()
    part1(actualInput).println()
//
    part2(testInput).println()
    part2(actualInput).println()
}
