package year2024

import println
import readInput
import readTestInput

fun main() {
    fun onSides(x: Int, y: Int, onSide: (Int, Int, Int) -> Unit) {
        onSide(x - 1, y, 0)
        onSide(x + 1, y, 2)
        onSide(x, y - 1, 1)
        onSide(x, y + 1, 3)
    }

    fun getAreaAndPerimeter(
        inputs: List<String>,
        x: Int,
        y: Int,
        char: Char,
        visited: MutableSet<Int>
    ): Long {
        var points = listOf(x, y)
        var perimeter = 0L
        val width = inputs[0].length
        val area = hashSetOf(x + y * width)
        while (points.isNotEmpty()) {
            points = buildList {
                for (i in points.indices step 2) {
                    onSides(points[i], points[i + 1]) { nextX, nextY, _ ->
                        if (inputs.getAt(nextX, nextY) == char) {
                            if (area.add(nextX + nextY * width)) {
                                add(nextX)
                                add(nextY)
                            }
                        } else {
                            perimeter++
                        }
                    }
                }
            }
        }
        visited.addAll(area)
        val score = perimeter * area.size
        return score
    }

    fun part1(inputs: List<String>): Long {
        var score = 0L
        val visited = hashSetOf<Int>()
        val width = inputs[0].length
        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                if (visited.contains(x + y * width)) {
                    continue
                }
                val char = line[x]
                score += getAreaAndPerimeter(inputs, x, y, char, visited)
            }
        }
        return score
    }

    fun calcSides(perimeterPoints: Set<Triple<Int, Int, Int>>): Int {
        var sides = 0
        val calculated = hashSetOf<Triple<Int, Int, Int>>()
        perimeterPoints.forEach { sidePoint ->
            if (calculated.add(sidePoint)) {
                sides++
                val (x, y, side) = sidePoint
                if (side % 2 == 0) {
                    var next = Triple(x, y - 1, side)
                    while (next in perimeterPoints) {
                        calculated.add(next)
                        next = next.copy(second = next.second - 1)
                    }
                    next = Triple(x, y + 1, side)
                    while (next in perimeterPoints) {
                        calculated.add(next)
                        next = next.copy(second = next.second + 1)
                    }
                } else {
                    var next = Triple(x - 1, y, side)
                    while (next in perimeterPoints) {
                        calculated.add(next)
                        next = next.copy(first = next.first - 1)
                    }
                    next = Triple(x + 1, y, side)
                    while (next in perimeterPoints) {
                        calculated.add(next)
                        next = next.copy(first = next.first + 1)
                    }
                }
            }
        }
        return sides
    }

    fun getAreaAndPerimeter2(
        inputs: List<String>,
        x: Int,
        y: Int,
        char: Char,
        visited: MutableSet<Int>
    ): Int {
        var points = listOf(x, y)
        val perimeter = hashSetOf<Triple<Int, Int, Int>>()
        val width = inputs[0].length
        val startSize = visited.size
        visited.add(x + y * width)
        while (points.isNotEmpty()) {
            points = buildList {
                for (i in points.indices step 2) {
                    onSides(points[i], points[i + 1]) { nextX, nextY, side ->
                        if (inputs.getAt(nextX, nextY) == char) {
                            if (visited.add(nextX + nextY * width)) {
                                add(nextX)
                                add(nextY)
                            }
                        } else {
                            perimeter.add(Triple(nextX, nextY, side))
                        }
                    }
                }
            }
        }
        return calcSides(perimeter) * (visited.size - startSize)
    }

    fun part2(inputs: List<String>): Long {
        var score = 0L
        val visited = hashSetOf<Int>()
        val width = inputs[0].length
        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                if (visited.contains(x + y * width)) {
                    continue
                }
                val char = line[x]
                score += getAreaAndPerimeter2(inputs, x, y, char, visited)
            }
        }
        return score
    }

    val testInput0 = readTestInput(2024, 12, "0")
    val testInput1 = readTestInput(2024, 12, "1")
    val testInput2 = readTestInput(2024, 12, "2")
    val actualInput = readInput(2024, 12)

    part1(testInput0).println()
    part1(testInput1).println()
    part1(testInput2).println()
    part1(actualInput).println()
    part2(testInput0).println()
    part2(testInput1).println()
    part2(actualInput).println()
}

private fun List<String>.getAt(x: Int, y: Int): Char? {
    val line = getOrNull(y) ?: return null
    return line.getOrNull(x)
}
