package year2024

import println
import readInput

fun main() {
    fun hasWord(inputs: List<String>, posX: Int, posY: Int, dirX: Int, dirY: Int): Boolean {
        var charsFound = 1
        var x = posX
        var y = posY
        while (charsFound < "XMAS".length) {
            x += dirX
            y += dirY
            if (y in inputs.indices && x in inputs[y].indices) {
                val char = inputs[y][x]
                if (char != "XMAS"[charsFound++]) {
                    return false
                }
            } else {
                return false
            }
        }
        return true
    }

    fun part1(inputs: List<String>): Int {
        var sum = 0
        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                val char = line[x]
                if (char == 'X') {
                    if (hasWord(inputs, x, y, 0, 1)) sum++
                    if (hasWord(inputs, x, y, 0, -1)) sum++
                    if (hasWord(inputs, x, y, -1, 0)) sum++
                    if (hasWord(inputs, x, y, 1, 0)) sum++
                    if (hasWord(inputs, x, y, 1, 1)) sum++
                    if (hasWord(inputs, x, y, 1, -1)) sum++
                    if (hasWord(inputs, x, y, -1, -1)) sum++
                    if (hasWord(inputs, x, y, -1, 1)) sum++
                }
            }
        }
        return sum
    }

    fun hasX(inputs: List<String>, posX: Int, posY: Int): Boolean {
        if (posY - 1 !in inputs.indices || posY + 1 !in inputs.indices) {
            return false
        }
        if (posX - 1 !in inputs[posY - 1].indices ||
            posX - 1 !in inputs[posY + 1].indices ||
            posX + 1 !in inputs[posY - 1].indices ||
            posX + 1 !in inputs[posY + 1].indices
        ) {
            return false
        }
        return (inputs[posY - 1][posX - 1] == 'M' && inputs[posY + 1][posX + 1] == 'S' ||
                inputs[posY - 1][posX - 1] == 'S' && inputs[posY + 1][posX + 1] == 'M') &&
                (inputs[posY - 1][posX + 1] == 'M' && inputs[posY + 1][posX - 1] == 'S' ||
                        inputs[posY - 1][posX + 1] == 'S' && inputs[posY + 1][posX - 1] == 'M')
    }

    fun part2(inputs: List<String>): Int {
        var sum = 0
        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                if (line[x] == 'A' && hasX(inputs, x, y)) sum++
            }
        }
        return sum
    }

    val sampleInput = readInput("Day2024-04-test")

    val actualInput = readInput(2024, 4)

    part1(sampleInput).println()
    part1(actualInput).println()

    part2(sampleInput).println()
    part2(actualInput).println()
}
