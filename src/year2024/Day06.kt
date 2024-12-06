package year2024

import println
import readInput
import readTestInput

fun main() {
    fun navigate(inputs: List<String>, startX: Int, startY: Int, dirX: Int, dirY: Int): Set<Int> {
        var positionX = startX
        var positionY = startY
        val width = inputs[0].length
        var moveX = dirX
        var moveY = dirY
        val visited = hashSetOf(positionX + positionY * width)
        while (true) {
            val nextY = positionY + moveY
            if (nextY !in inputs.indices) return visited
            val nextX = positionX + moveX
            if (nextX !in inputs[nextY].indices) return visited
            val nextChar = inputs[nextY][nextX]
            if (nextChar == '#') {
                val swap = moveX
                moveX = -moveY
                moveY = swap
            } else {
                positionX = nextX
                positionY = nextY
                visited.add(positionX + positionY * width)
            }
        }
    }

    fun part1(inputs: List<String>): Int {
        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                val char = line[x]
                when (char) {
                    '^' -> return navigate(inputs, x, y, 0, -1).size
                    '<' -> return navigate(inputs, x, y, -1, 0).size
                    '>' -> return navigate(inputs, x, y, 1, 0).size
                    'v' -> return navigate(inputs, x, y, 0, 1).size
                }
            }
        }
        return 0
    }

    fun isLooping(inputs: List<String>, startX: Int, startY: Int, dirX: Int, dirY: Int, obsX: Int, obsY: Int): Boolean {
        if (obsX == startX && obsY == startY) return false
        var positionX = startX
        var positionY = startY
        val width = inputs[0].length
        var moveX = dirX
        var moveY = dirY
        val visited = hashSetOf(positionX + positionY * width to moveX * 2 + moveY)
        while (true) {
            val nextY = positionY + moveY
            if (nextY !in inputs.indices) return false
            val nextX = positionX + moveX
            if (nextX !in inputs[nextY].indices) return false
            if (nextY == obsY && nextX == obsX || inputs[nextY][nextX] == '#') {
                val swap = moveX
                moveX = -moveY
                moveY = swap
            } else {
                positionX = nextX
                positionY = nextY
                if (!visited.add(positionX + positionY * width to moveX * 2 + moveY)) {
                    return true
                }
            }
        }
    }

    fun findLoopsFrom(inputs: List<String>, startX: Int, startY: Int, dirX: Int, dirY: Int): Int {
        val width = inputs[0].length
        return navigate(inputs, startX, startY, dirX, dirY).count { combined ->
            isLooping(inputs, startX, startY, dirX, dirY, combined % width, combined / width)
        }
    }

    fun part2(inputs: List<String>): Int {
        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                val char = line[x]
                when (char) {
                    '^' -> return findLoopsFrom(inputs, x, y, 0, -1)
                    '<' -> return findLoopsFrom(inputs, x, y, -1, 0)
                    '>' -> return findLoopsFrom(inputs, x, y, 1, 0)
                    'v' -> return findLoopsFrom(inputs, x, y, 0, 1)
                }
            }
        }
        return 0
    }

    val testInputs = readTestInput(2024, 6)
    val actualInput = readInput(2024, 6)

    part1(testInputs).println()
    part2(testInputs).println()
    part1(actualInput).println()
    part2(actualInput).println()
}
