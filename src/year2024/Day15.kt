package year2024

import println
import readInput
import readTestInput

fun main() {
    fun move(robotX: Int, robotY: Int, map: List<CharArray>, dirX: Int, dirY: Int): Boolean {
        var x = robotX
        var y = robotY

        var swap = false
        while (true) {
            x += dirX
            y += dirY
            val value = map[y][x]
            if (value == '#') return false
            if (value == '.') {
                if (swap) {
                    map[y][x] = map[robotY + dirY][robotX + dirX]
                    map[robotY + dirY][robotX + dirX] = '.'
                }
                return true
            }
            swap = true
        }
    }

    fun part1(inputs: List<String>): Int {
        val map = ArrayList<CharArray>()
        var operations = emptyList<String>()
        var robotX = -1
        var robotY = -1
        for (y in inputs.indices) {
            val line = inputs[y]
            if (line.isBlank()) {
                operations = inputs.subList(y + 1, inputs.size)
                break
            } else {
                val chars = line.toCharArray()
                val robotIndex = line.indexOf('@')
                if (robotIndex != -1) {
                    chars[robotIndex] = '.'
                    robotX = robotIndex
                    robotY = y
                }
                map.add(chars)
            }
        }

        for (i in operations.indices) {
            val operationLine = operations[i]
            for (j in operationLine.indices) {
                val char = operationLine[j]
                var updateX: Int
                var updateY: Int
                when (char) {
                    '>' -> {
                        updateX = 1
                        updateY = 0
                    }

                    '<' -> {
                        updateX = -1
                        updateY = 0
                    }

                    'v' -> {
                        updateX = 0
                        updateY = 1
                    }

                    '^' -> {
                        updateX = 0
                        updateY = -1
                    }

                    else -> continue
                }
                if (move(robotX, robotY, map, updateX, updateY)) {
                    robotX += updateX
                    robotY += updateY
                }
            }
        }

        var sum = 0

        for (y in map.indices) {
            val line = map[y]
            for (x in line.indices) {
                val char = line[x]
                if (char == 'O') {
                    sum += y * 100 + x
                }
            }
        }

        return sum
    }

    fun moveRocks(robotX: Int, robotY: Int, map: List<CharArray>, dirY: Int) {
        var y = robotY
        var ongoing2 = setOf(robotX to '.')
        while (ongoing2.isNotEmpty()) {
            y += dirY
            val line2 = map[y]
            val updated = hashSetOf<Int>()
            val newOngoing2 = hashSetOf<Pair<Int, Char>>()
            ongoing2.forEach { (index, char) ->
                val value = line2[index]
                line2[index] = char
                updated.add(index)
                if (value == '[') {
                    newOngoing2.add(index to '[')
                    newOngoing2.add(index + 1 to ']')
                } else if (value == ']') {
                    newOngoing2.add(index - 1 to '[')
                    newOngoing2.add(index to ']')
                }
            }
            newOngoing2.forEach { (index, _) ->
                if (updated.add(index)) {
                    line2[index] = '.'
                }
            }
            ongoing2 = newOngoing2
        }
    }

    fun moveRocksIfPossible(robotX: Int, robotY: Int, map: List<CharArray>, dirX: Int, dirY: Int): Boolean {
        if (dirX != 0) {
            var x = robotX
            val line = map[robotY]
            while (true) {
                x += dirX
                val value = line[x]
                if (value == '#') return false
                if (value == '.') {
                    if (robotX + dirX == x) return true
                    line[robotX + dirX] = '.'
                    if (dirX > 0) {
                        for (x1 in robotX + 2..x step 2) {
                            line[x1] = '['
                            line[x1 + 1] = ']'
                        }
                    } else {
                        for (x1 in robotX - 2 downTo x step 2) {
                            line[x1 - 1] = '['
                            line[x1] = ']'
                        }
                    }
                    return true
                }
                x += dirX
            }
        } else {
            var y = robotY
            var ongoing = setOf(robotX)
            while (true) {
                y += dirY
                val line = map[y]
                val newOngoing = hashSetOf<Int>()
                ongoing.forEach { index ->
                    val value = line[index]
                    if (value == '#') return false
                    if (value == '[') {
                        newOngoing.add(index)
                        newOngoing.add(index + 1)
                    } else if (value == ']') {
                        newOngoing.add(index - 1)
                        newOngoing.add(index)
                    }
                }
                if (newOngoing.isEmpty()) {
                    if (robotY + dirY == y) return true
                    moveRocks(robotX, robotY, map, dirY)
                    return true
                }
                ongoing = newOngoing
            }
        }
    }

    fun part2(inputs: List<String>): Int {
        val map = ArrayList<CharArray>()
        var operations = emptyList<String>()
        var robotX = -1
        var robotY = -1
        for (y in inputs.indices) {
            val line = inputs[y]
            if (line.isBlank()) {
                operations = inputs.subList(y + 1, inputs.size)
                break
            } else {
                val chars = CharArray(line.length * 2)
                for (x in line.indices) {
                    val mapX = x * 2
                    val char = line[x]
                    when (char) {
                        '@' -> {
                            robotX = mapX
                            robotY = y
                            chars[mapX] = '.'
                            chars[mapX + 1] = '.'
                        }

                        '.' -> {
                            chars[mapX] = '.'
                            chars[mapX + 1] = '.'
                        }

                        'O' -> {
                            chars[mapX] = '['
                            chars[mapX + 1] = ']'
                        }

                        else -> {
                            chars[mapX] = '#'
                            chars[mapX + 1] = '#'
                        }
                    }
                }
                map.add(chars)
            }
        }

        for (i in operations.indices) {
            val operationLine = operations[i]
            for (j in operationLine.indices) {
                val char = operationLine[j]
                var updateX: Int
                var updateY: Int
                when (char) {
                    '>' -> {
                        updateX = 1
                        updateY = 0
                    }

                    '<' -> {
                        updateX = -1
                        updateY = 0
                    }

                    'v' -> {
                        updateX = 0
                        updateY = 1
                    }

                    '^' -> {
                        updateX = 0
                        updateY = -1
                    }

                    else -> continue
                }
                if (moveRocksIfPossible(robotX, robotY, map, updateX, updateY)) {
                    robotX += updateX
                    robotY += updateY
                }
            }
        }

        var sum = 0

        for (y in map.indices) {
            val line = map[y]
            for (x in line.indices) {
                val char = line[x]
                if (char == '[') {
                    sum += y * 100 + x
                }
            }
        }

        return sum
    }

    val testInput = readTestInput(2024, 15)
    val testInput2 = readTestInput(2024, 15, "2")
    val actualInput = readInput(2024, 15)
    val testInput3 = readTestInput(2024, 15, "3")

    part1(testInput).println()
    part1(testInput2).println()
    print("part 1 actual ")
    part1(actualInput).println()

    part2(testInput3).println()
    part2(testInput).println()
    print("part 2 actual ")
    part2(actualInput).println()
}
