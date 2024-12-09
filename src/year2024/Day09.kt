package year2024

import println
import readInput
import readTestInput

fun main() {
    fun IntArray.indexLastFrom(from: Int, predicate: (Int) -> Boolean): Int {
        for (index in from downTo 0) {
            if (predicate(this[index])) {
                return index
            }
        }
        return -1
    }

    fun IntArray.indexOfFrom(value: Int, from: Int): Int {
        for (index in from until size) {
            if (this[index] == value) {
                return index
            }
        }
        return -1
    }

    fun part1(inputs: List<String>): Long {
        val line = inputs.first()
        var isFile = true
        var printId = 0
        val updated: List<Int> = buildList {
            for (i in line.indices) {
                val value = line[i].digitToInt()
                if (isFile) {
                    repeat(value) { add(printId) }
                    printId++
                } else {
                    repeat(value) { add(-1) }
                }
                isFile = !isFile
            }
        }

        val intArray = IntArray(updated.size) { updated[it] }

        var lastValueIndex = intArray.indexOfLast { it != -1 }
        var freeIndex = updated.indexOf(-1)
        while (freeIndex < lastValueIndex) {
            intArray[freeIndex] = intArray[lastValueIndex]
            intArray[lastValueIndex] = -1
            lastValueIndex = intArray.indexLastFrom(lastValueIndex) { it != -1 }
            freeIndex = intArray.indexOfFrom(-1, freeIndex)
        }

        var sum = 0L

        for (i in 0 until freeIndex) {
            sum += intArray[i] * i
        }

        return sum
    }

    fun part2(inputs: List<String>): Long {
        val line = inputs.first()
        var isFile = true
        var printId = 0
        val fileCount = if (line.length % 2 == 0) line.length / 2 else line.length / 2 + 1
        val files = IntArray(fileCount)
        val fileIndexes = IntArray(fileCount)
        val updated: List<Int> = buildList {
            for (i in line.indices) {
                val value = line[i].digitToInt()
                if (isFile) {
                    fileIndexes[printId] = this.size
                    repeat(value) { add(printId) }
                    files[printId] = value
                    printId++
                } else {
                    repeat(value) { add(-1) }
                }
                isFile = !isFile
            }
        }

        val intArray = IntArray(updated.size) { updated[it] }
        val originalSizes = IntArray(fileCount) { files[it] }
        for (fileId in files.lastIndex downTo 1) {
            val size = originalSizes[fileId]
            for (otherFile in 0 until fileId) {
                val otherFileSize = files[otherFile]
                val otherFileStart = fileIndexes[otherFile]
                val space = fileIndexes[otherFile + 1] - otherFileStart - otherFileSize
                if (space >= size) {
                    files[otherFile] = otherFileSize + size
                    val start = otherFileStart + otherFileSize
                    val originalStart = fileIndexes[fileId]
                    repeat(size) {
                        intArray[start + it] = fileId
                        intArray[originalStart + it] = -1
                    }
                    break
                }
            }
        }

        var sum = 0L

        for (i in intArray.indices) {
            val value = intArray[i]
            if (value != -1) {
                sum += intArray[i] * i
            }
        }

        return sum
    }

    val testInput = readTestInput(2024, 9)
    val actualInput = readInput(2024, 9)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
