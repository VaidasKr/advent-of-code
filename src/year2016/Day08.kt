package year2016

import println
import readInput

fun main() {
    val commands = listOf(
        "rect 3x2", "rotate column x=1 by 1", "rotate row y=0 by 4", "rotate column x=1 by 1"
    )

    run(7, 3, commands).println()

    val actualInput = readInput(2016, 8)

    run(50, 6, actualInput).println()
}

private fun run(width: Int, height: Int, commands: List<String>): Int {
    val grid = Array(height) { BooleanArray(width) { false } }
    val rowBuffer = BooleanArray(width)
    val columnBuffer = BooleanArray(height)

    fun create(width: Int, height: Int) {
        for (x in 0 until width) {
            for (y in 0 until height) {
                grid[y][x] = true
            }
        }
    }

    fun rotateColumn(column: Int, size: Int) {
        if (size % height == 0) return
        for (y in 0 until height) {
            val endPos = (y + size) % height
            columnBuffer[endPos] = grid[y][column]
        }
        for (y in 0 until height) {
            grid[y][column] = columnBuffer[y]
        }
    }

    fun rotateRow(row: Int, size: Int) {
        if (size % width == 0) return
        for (x in 0 until width) {
            val endPos = (x + size) % width
            rowBuffer[endPos] = grid[row][x]
        }
        grid[row] = rowBuffer.copyInto(grid[row])
    }

    commands.forEach { command ->
        if (command.startsWith("rect")) {
            val (rWidth, rHeight) = command.substring(5).split('x')
            create(rWidth.toInt(), rHeight.toInt())
        } else if (command.startsWith("rotate row")) {
            val (row, size) = command.substring(13).split(" by ")
            rotateRow(row.toInt(), size.toInt())
        } else if (command.startsWith("rotate column")) {
            val (column, size) = command.substring(16).split(" by ")
            rotateColumn(column.toInt(), size.toInt())
        }
    }

    fun printGrid() {
        print('┌')
        print("-".repeat(width))
        println('┐')
        for (y in 0 until height) {
            print('|')
            val row = grid[y]
            for (x in 0 until width) {
                if (row[x]) {
                    print('■')
                } else {
                    print(' ')
                }
            }
            println('|')
        }

        print('└')
        print("-".repeat(width))
        println('┘')
    }

    printGrid()

    return grid.sumOf { row -> row.count { cell -> cell } }
}
