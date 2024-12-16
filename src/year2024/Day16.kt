package year2024

import println
import readInput
import readTestInput
import java.util.PriorityQueue

inline fun getSides(dirX: Int, dirY: Int, onSide: (Int, Int) -> Unit) {
    onSide(dirY, dirX)
    onSide(-dirY, -dirX)
}

fun main() {
    fun getLowestScore(inputs: List<String>, startX: Int, startY: Int): Int {
        val queue = PriorityQueue<List<Int>>(compareBy { it[4] })
        queue.add(listOf(startX, startY, 1, 0, 0))
        queue.add(listOf(startX, startY, 0, 1, 1000))
        queue.add(listOf(startX, startY, 0, -1, 1000))
        queue.add(listOf(startX, startY, -1, 0, 2000))
        val scoreCache = HashMap<List<Int>, Int>()
        while (queue.isNotEmpty()) {
            val (x, y, dirX, dirY, score) = queue.poll()
            var nextX = x
            var nextY = y
            var distance = 0
            while (true) {
                nextX += dirX
                nextY += dirY
                distance++
                val newScore = score + distance
                val char = inputs[nextY][nextX]
                if (char == 'E') {
                    return newScore
                }

                val key = listOf(nextX, nextY, dirX, dirY)
                val cachedScore = scoreCache.getOrElse(key) { Int.MAX_VALUE }
                if (cachedScore < newScore) {
                    break
                }
                scoreCache[key] = newScore

                if (char == '.') {
                    queue.add(listOf(nextX, nextY, dirY, dirX, score + 1000 + distance))
                    queue.add(listOf(nextX, nextY, -dirY, -dirX, score + 1000 + distance))
                } else {
                    break
                }
            }
        }
        return 0
    }

    fun part1(inputs: List<String>): Int {
        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                if (line[x] == 'S') {
                    return getLowestScore(inputs, x, y)
                }
            }
        }
        throw IllegalStateException("no start")
    }

    fun getLowestScoreLength(inputs: List<String>, startX: Int, startY: Int): Int {
        val width = inputs.first().length
        val queue = PriorityQueue<List<Int>>(compareBy { it[2] })
        val startPos = startX + startY * width
        queue.add(listOf(1, 0, 0, startPos))
        queue.add(listOf(0, 1, 1000, startPos))
        queue.add(listOf(0, -1, 1000, startPos))
        queue.add(listOf(-1, 0, 2000, startPos))
        val best = hashSetOf<Int>()
        var min = Int.MAX_VALUE
        val scoreCache = HashMap<List<Int>, Int>()
        while (queue.isNotEmpty()) {
            val values = queue.poll()
            val (dirX, dirY, score) = values
            val path = values.drop(3).toMutableList()
            val nextPos = values.last()
            var nextX = nextPos % width
            var nextY = nextPos / width
            var distance = 0
            while (true) {
                nextX += dirX
                nextY += dirY
                distance++
                path += nextX + nextY * width
                val newScore = score + distance
                val char = inputs[nextY][nextX]
                if (char == 'E') {
                    if (newScore <= min) {
                        min = newScore
                    } else {
                        return best.size
                    }
                    best.addAll(path)
                }
                val key = listOf(nextX, nextY, dirX, dirY)
                if (scoreCache.getOrElse(key) { Int.MAX_VALUE } < newScore) {
                    break
                }
                scoreCache[key] = newScore

                if (char == '#') break
                queue.add(listOf(dirY, dirX, score + 1000 + distance) + path)
                queue.add(listOf(-dirY, -dirX, score + 1000 + distance) + path)
            }
        }
        return best.size
    }

    fun part2(inputs: List<String>): Int {
        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                if (line[x] == 'S') {
                    return getLowestScoreLength(inputs, x, y)
                }
            }
        }
        throw IllegalStateException("no start")
    }

    val testInput1 = readTestInput(2024, 16, "1")
    val testInput2 = readTestInput(2024, 16, "2")
    val actualInput = readInput(2024, 16)

    println("part 1")
    part1(testInput1).println()
    part1(testInput2).println()
    part1(actualInput).println()

    println("part 2")
    part2(testInput1).println()
    part2(testInput2).println()
    part2(actualInput).println()
}
