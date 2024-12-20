package year2024

import println
import readInput
import readTestInput

private inline fun getSides20(onSide: (Int, Int) -> Unit) {
    onSide(+1, 0)
    onSide(-1, 0)
    onSide(0, -1)
    onSide(0, +1)
}

private inline fun getSides20(x: Int, y: Int, onSide: (Int, Int) -> Unit) {
    onSide(x + 1, y)
    onSide(x - 1, y)
    onSide(x, y - 1)
    onSide(x, y + 1)
}

fun main() {
    fun List<String>.getAt(x: Int, y: Int): Char {
        if (y !in indices) return '-'
        val line = this[y]
        if (x !in line.indices) return '-'
        return line[x]
    }

    fun shortestPathFromTo(inputs: List<String>, start: Int, end: Int): List<Int> {
        val visited = hashSetOf(start)
        var ongoing = listOf(listOf(start))
        var steps = 0
        val width = inputs[0].length
        while (ongoing.isNotEmpty()) {
            steps++
            val newOngoing = mutableListOf<List<Int>>()
            for (i in ongoing.indices) {
                val path = ongoing[i]
                val position = path.last()
                getSides20(position % width, position / width) { newX, newY ->
                    val char = inputs.getAt(newX, newY)
                    if (char != '-') {
                        val newPoint = newX + newY * width
                        if (newPoint == end) return path + newPoint
                        if (inputs[newY][newX] != '#' && visited.add(newPoint)) {
                            newOngoing.add(path + newPoint)
                        }
                    }
                }
            }
            ongoing = newOngoing
        }
        return emptyList()
    }

    fun getShortestPath(inputs: List<String>, width: Int): List<Int> {
        var start = -1
        var end = -1
        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                val char = line[x]
                if (char == 'S') {
                    start = x + y * width
                } else if (char == 'E') {
                    end = x + y * width
                }
            }
        }
        if (start == -1 || end == -1) throw IllegalStateException("no start or end")

        return shortestPathFromTo(inputs, start, end)
    }

    fun part1(inputs: List<String>): Int {
        val width = inputs[0].length
        val fastest = getShortestPath(inputs, width)

        var sum = 0

        val map = HashMap<Int, Int>(fastest.size)
        for (i in fastest.indices) {
            map[fastest[i]] = i
        }

        for (i in fastest.indices) {
            val position = fastest[i]
            val x = position % width
            val y = position / width
            getSides20 { dirX, dirY ->
                val endX = x + dirX + dirX
                val endY = y + dirY + dirY
                if (inputs.getAt(x + dirX, y + dirY) == '#' &&
                    inputs.getAt(endX, endY).let { it != '-' && it != '#' }
                ) {
                    val saved = map[endX + width * endY]!! - i - 2
                    if (saved >= 100) {
                        sum++
                    }
                }
            }
        }

        return sum
    }

    fun getCheats(inputs: List<String>, position: Int, width: Int, max: Int, onCheated: (Int, Int) -> Unit) {
        val visited = hashSetOf(position)
        var ongoing = listOf(position)
        var steps = 0
        while (ongoing.isNotEmpty() && steps < max) {
            steps++
            ongoing = buildList {
                for (i in ongoing.indices) {
                    val point = ongoing[i]
                    getSides20(point % width, point / width) { newX, newY ->
                        val char = inputs.getAt(newX, newY)
                        if (char != '-') {
                            val newPoint = newX + newY * width
                            if (visited.add(newPoint)) {
                                if (char == '#') {
                                    add(newPoint)
                                } else {
                                    if (steps > 1) onCheated(newPoint, steps)
                                    add(newPoint)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun part2(inputs: List<String>, threshold: Int, cheats: Int): Int {
        val width = inputs[0].length
        val fastest = getShortestPath(inputs, width)
        val map = HashMap<Int, Int>(fastest.size)
        for (i in fastest.indices) {
            map[fastest[i]] = i
        }
        var sum = 0
        for (i in fastest.indices) {
            val position = fastest[i]
            getCheats(inputs, position, width, cheats) { pos, steps ->
                if (map[pos]!! - i - steps >= threshold) {
                    sum++
                }
            }
        }
        return sum
    }

    val testInput = readTestInput(2024, 20)
    val actualInput = readInput(2024, 20)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput, 50, 20).println()
    part2(actualInput, 100, 20).println()
}
