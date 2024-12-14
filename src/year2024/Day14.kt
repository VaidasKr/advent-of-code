package year2024

import println
import readInput
import readTestInput

fun main() {
    fun part1(inputs: List<String>, width: Int, height: Int, iterations: Int): Int {
        val middleX = width / 2
        val middleY = height / 2
        val quadrantCount = IntArray(4)
        for (i in inputs.indices) {
            val line = inputs[i]
            if (line.isBlank()) break
            val start = line.indexOf('=')
            val endIndex = line.indexOf(',')
            val startX = line.substring(start + 1, endIndex).toInt()
            val startY = line.substring(endIndex + 1, line.indexOf(' ')).toInt()
            val vEndIndex = line.indexOf(',', endIndex + 1)
            val vX = line.substring(line.indexOf('=', endIndex) + 1, vEndIndex).toInt()
            val vY = line.substring(vEndIndex + 1).toInt()
            val endX = (startX + vX * iterations).mod(width)
            if (endX == middleX) continue
            val endY = (startY + vY * iterations).mod(height)
            if (endY == middleY) continue
            if (endX < middleX) {
                if (endY < middleY) {
                    quadrantCount[0] = quadrantCount[0] + 1
                } else {
                    quadrantCount[1] = quadrantCount[1] + 1
                }
            } else {
                if (endY < middleY) {
                    quadrantCount[2] = quadrantCount[2] + 1
                } else {
                    quadrantCount[3] = quadrantCount[3] + 1
                }
            }
        }
        var result = 1
        quadrantCount.forEach {
            result *= it
        }
        return result
    }

    fun drawAndReset(points: Array<BooleanArray>, width: Int, height: Int) {
        for (y in 0 until height) {
            val array = points[y]
            for (x in 0 until width) {
                if (array[x]) {
                    array[x] = false
                    print('█')
                } else {
                    print(' ')
                }
            }
            println()
        }
        println()
    }

    fun reset(points: Array<BooleanArray>, width: Int, height: Int) {
        for (y in 0 until height) {
            val array = points[y]
            for (x in 0 until width) {
                array[x] = false
            }
        }
    }

    fun check(points: Array<BooleanArray>, width: Int, height: Int): Boolean {
        for (y in 0 until height) {
            val array = points[y]
            var matches = 0
            for (x in 0 until width) {
                if (array[x]) {
                    matches++
                } else {
                    if (matches > 30) {
                        return true
                    }
                    matches = 0
                }
            }
        }
        return false
    }

    fun part2(inputs: List<String>, width: Int, height: Int): Int {
        val robots = buildList {
            for (i in inputs.indices) {
                val line = inputs[i]
                if (line.isBlank()) break
                val start = line.indexOf('=')
                val endIndex = line.indexOf(',')
                val startX = line.substring(start + 1, endIndex).toInt()
                val startY = line.substring(endIndex + 1, line.indexOf(' ')).toInt()
                val vEndIndex = line.indexOf(',', endIndex + 1)
                val vX = line.substring(line.indexOf('=', endIndex) + 1, vEndIndex).toInt()
                val vY = line.substring(vEndIndex + 1).toInt()
                add(intArrayOf(startX, startY, vX, vY))
            }
        }
        var iteration = 0
        val drawMap = Array(height) { BooleanArray(width) }
        while (true) {
            for (i in robots.indices) {
                val robot = robots[i]
                val endX = (robot[0] + robot[2] * iteration).mod(width)
                val endY = (robot[1] + robot[3] * iteration).mod(height)
                drawMap[endY][endX] = true
            }
            if (check(drawMap, width, height)) {
//                drawAndReset(drawMap, width, height)
                return iteration
            } else {
                reset(drawMap, width, height)
            }
            iteration++
        }
    }

    val testInput = readTestInput(2024, 14)
    val actualInput = readInput(2024, 14)

    part1(testInput, 11, 7, 100).println()
    part1(actualInput, 101, 103, 100).println()

    part2(actualInput, 101, 103).println()
}
