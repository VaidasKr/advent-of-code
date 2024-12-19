package year2024

import println
import readInput
import readTestInput

inline fun neighbours(x: Int, y: Int, onNeighbours: (Int, Int) -> Unit) {
    onNeighbours(x + 1, y)
    onNeighbours(x - 1, y)
    onNeighbours(x, y + 1)
    onNeighbours(x, y - 1)
}

fun main() {
    fun part1(inputs: List<String>, size: Int, bytes: Int): Int {
        val width = size + 1
        val corruptedPoints = hashSetOf<Int>()
        for (i in 0 until bytes) {
            val (x, y) = inputs[i].split(',')
            corruptedPoints.add(x.toInt() + y.toInt() * width)
        }

        var steps = 0
        val allowedRange = 0..size
        var ongoing = listOf(0, 0)
        corruptedPoints.add(0)
        while (ongoing.isNotEmpty()) {
            steps++
            ongoing = buildList {
                for (i in ongoing.indices step 2) {
                    neighbours(ongoing[i], ongoing[i + 1]) { x, y ->
                        if (x == size && y == size) return steps
                        if (x in allowedRange && y in allowedRange && corruptedPoints.add(x + y * width)) {
                            add(x)
                            add(y)
                        }
                    }
                }
            }
        }
        return 0
    }

    fun getBestPath(corrupted: Set<Int>, range: IntRange, width: Int, end: Int): Set<Int>? {
        var ongoing = setOf(listOf(0))
        val visited = hashSetOf(0)
        while (ongoing.isNotEmpty()) {
            val newOngoing = hashSetOf<List<Int>>()
            ongoing.forEach { path ->
                val point = path.last()
                neighbours(point % width, point / width) { x, y ->
                    if (x == end && y == end) return path.toSet()
                    val newPoint = x + y * width
                    if (x in range && y in range && newPoint !in corrupted && visited.add(newPoint)) {
                        newOngoing.add(path + newPoint)
                    }
                }
            }
            ongoing = newOngoing
        }
        return null
    }

    fun part2(inputs: List<String>, size: Int): String {
        val width = size + 1
        val allowedRange = 0..size
        val corruptedPoints = hashSetOf<Int>()
        var bestPath = emptySet<Int>()
        for (lineI in inputs.indices) {
            val line = inputs[lineI]
            val (x, y) = line.split(',')
            val newPoint = x.toInt() + y.toInt() * width
            corruptedPoints.add(newPoint)

            if (bestPath.isNotEmpty() && newPoint !in bestPath) {
                continue
            }
            val newBestPath = getBestPath(corruptedPoints, allowedRange, width, size) ?: return line
            bestPath = newBestPath
        }
        return "nope"
    }

    val testInput = readTestInput(2024, 18)
    val actualInput = readInput(2024, 18)

    part1(testInput, 6, 12).println()
    part1(actualInput, 70, 1024).println()
    part2(testInput, 6).println()
    part2(actualInput, 70).println()
}
