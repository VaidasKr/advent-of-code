package year2025

import printCompare
import println
import readInput

private inline fun List<String>.canPick(x: Int, y: Int, isCotton: (Int, Int) -> Boolean): Boolean {
    var neighbours = 0
    onNeighbours(x, y) { checkX, checkY ->
        if (isCotton(checkX, checkY)) neighbours++
        if (neighbours >= 4) return false
    }
    return true
}

private inline fun String.onSidesOf(x: Int, onSide: (Int) -> Unit) {
    if (x > 0) onSide(x - 1)
    if (x < lastIndex) onSide(x + 1)
}

private inline fun String.onNeighbours(x: Int, onNeighbour: (Int) -> Unit) {
    if (x > 0) onNeighbour(x - 1)
    onNeighbour(x)
    if (x < lastIndex) onNeighbour(x + 1)
}

private inline fun List<String>.onNeighbours(
    x: Int,
    y: Int,
    onNeighbour: (Int, Int) -> Unit
) {
    if (y > 0) get(y - 1).onNeighbours(x) { onNeighbour(it, y - 1) }
    get(y).onSidesOf(x) { onNeighbour(it, y) }
    if (y < lastIndex) get(y + 1).onNeighbours(x) { onNeighbour(it, y + 1) }
}

fun main() {
    fun part1(inputs: List<String>): Long {
        var count = 0L

        for (y in inputs.indices) {
            val line = inputs[y]
            for (x in line.indices) {
                if (line[x] == '@' &&
                    inputs.canPick(x, y) { checkX, checkY -> inputs[checkY][checkX] == '@' }
                ) {
                    count++
                }
            }
        }

        return count
    }

    fun part2(inputs: List<String>): Long {
        var count = 0L
        val cleared = HashSet<Int>()

        val width = inputs.first().length

        val isCotton: (Int, Int) -> Boolean = { checkX, checkY ->
            inputs[checkY][checkX] == '@' && (checkY * width + checkX) !in cleared
        }

        while (true) {
            var currentClear = 0L

            for (y in inputs.indices) {
                val line = inputs[y]
                for (x in line.indices) {
                    if (isCotton(x, y) && inputs.canPick(x, y, isCotton)) {
                        cleared.add(y * line.length + x)
                        currentClear++
                    }
                }
            }

            if (currentClear == 0L) break
            count += currentClear
        }

        return count
    }

    val testInput = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
    """.trimIndent()
        .lines()
    val actualInput = readInput(2025, 4)

    part1(testInput).printCompare(13)
    part1(actualInput).printCompare(1367)

    part2(testInput).printCompare(43)
    part2(actualInput).printCompare(9144)
}
