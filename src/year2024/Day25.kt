package year2024

import println
import readInput
import readTestInput

fun main() {
    fun solve(inputs: List<String>): Int {
        val keys = arrayListOf<IntArray>()
        val locks = arrayListOf<IntArray>()
        var lineIndex = 0
        val keyIndices = 0 until 5
        while (lineIndex < inputs.size) {
            val line = inputs[lineIndex++]
            if (line.isBlank()) continue
            val value = IntArray(5)
            if (line == ".....") {
                repeat(5) { index ->
                    val next = inputs[lineIndex + index]
                    for (k in keyIndices) {
                        if (next[k] == '#') {
                            value[k] = value[k] + 1
                        }
                    }
                }
                keys.add(value)
            } else {
                repeat(6) { index ->
                    val next = inputs[lineIndex + index]
                    for (k in keyIndices) {
                        if (next[k] == '#') {
                            value[k] = value[k] + 1
                        }
                    }
                }
                locks.add(value)
            }
            lineIndex += 6
        }
        var matches = 0
        val keysIndices = keys.indices
        for (l in locks.indices) {
            val lock = locks[l]
            keys@ for (k in keysIndices) {
                val key = keys[k]
                for (i in keyIndices) {
                    if (lock[i] + key[i] > 5) continue@keys
                }
                matches++
            }
        }
        return matches
    }

    val testInput = readTestInput(2024, 25)
    val actualInput = readInput(2024, 25)

    solve(testInput).println()
    solve(actualInput).println()
}
