package year2024

import println
import readInput
import readTestInput
import java.util.PriorityQueue
import kotlin.time.measureTime

private inline fun getSides21(x: Int, y: Int, onSide: (Int, Int, Char) -> Unit) {
    onSide(x + 1, y, '>')
    onSide(x - 1, y, '<')
    onSide(x, y - 1, '^')
    onSide(x, y + 1, 'v')
}

fun main() {
    val keyPad = arrayOf("789", "456", "123", " 0A")
    val keyPadX = hashMapOf<Char, Int>()
    val keyPadY = hashMapOf<Char, Int>()
    for (y in keyPad.indices) {
        val line = keyPad[y]
        for (x in line.indices) {
            val char = line[x]
            keyPadX[char] = x
            keyPadY[char] = y
        }
    }

    val dPad = arrayOf(" ^A", "<v>")
    val dPadX = hashMapOf<Char, Int>()
    val dPadY = hashMapOf<Char, Int>()
    for (y in dPad.indices) {
        val line = dPad[y]
        for (x in line.indices) {
            val char = line[x]
            dPadX[char] = x
            dPadY[char] = y
        }
    }

    fun getDirectionsPath(
        pad: Array<String>,
        xMap: Map<Char, Int>,
        yMap: Map<Char, Int>,
        start: Char,
        target: Char,
        onPath: (String) -> Unit
    ) {
        if (start == target) {
            onPath("A")
            return
        }
        var ongoing = listOf("" to start)
        var noEnd = true
        while (ongoing.isNotEmpty() && noEnd) {
            ongoing = buildList {
                for (i in ongoing.indices) {
                    val (path, point) = ongoing[i]
                    getSides21(xMap[point]!!, yMap[point]!!) { newX, newY, dir ->
                        val char = pad.getOrElse(newY) { "" }.getOrElse(newX) { ' ' }
                        if (char != ' ') {
                            if (char == target) {
                                noEnd = false
                                onPath(path + dir + 'A')
                            } else {
                                add(path + dir to char)
                            }
                        }
                    }
                }
            }
        }
    }

    fun getDirectionsPath(
        pad: Array<String>,
        xMap: Map<Char, Int>,
        yMap: Map<Char, Int>,
        start: Char,
        target: Char
    ): Sequence<String> {
        if (start == target) {
            return sequenceOf("A")
        }
        var ongoing = listOf("" to start)
        var noEnd = true
        return sequence {
            while (ongoing.isNotEmpty() && noEnd) {
                ongoing = buildList {
                    for (i in ongoing.indices) {
                        val (path, point) = ongoing[i]
                        getSides21(xMap[point]!!, yMap[point]!!) { newX, newY, dir ->
                            val char = pad.getOrElse(newY) { "" }.getOrElse(newX) { ' ' }
                            if (char != ' ') {
                                if (char == target) {
                                    noEnd = false
                                    yield(path + dir + 'A')
                                } else {
                                    add(path + dir to char)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun getLineScore(
        line: String,
        pad: Array<String>,
        xMap: Map<Char, Int>,
        yMap: Map<Char, Int>
    ): List<String> {
        var position = 'A'
        var possibilities = listOf("")
        for (i in line.indices) {
            val char = line[i]
            possibilities = buildList {
                getDirectionsPath(pad, xMap, yMap, position, char) { newPath ->
                    for (j in possibilities.indices) {
                        add(possibilities[j] + newPath)
                    }
                }
            }
            position = char
        }

        return possibilities
    }

    fun getMinSequenceLength(line: String, roboCount: Int): Int {
        val queue = PriorityQueue<Pair<String, Int>>(compareBy { length -> length.first.length })
        getLineScore(line, keyPad, keyPadX, keyPadY).forEach {
            queue.add(it to 0)
        }
        val lengthMap = hashMapOf(0 to Int.MAX_VALUE)
        while (queue.isNotEmpty()) {
            val (possibility, mutations) = queue.poll()
            if (mutations >= roboCount || lengthMap[mutations]!! < possibility.length) {
                continue
            }
            getLineScore(possibility, dPad, dPadX, dPadY).forEach { newPossibility ->
                val cachedLength = lengthMap[mutations + 1] ?: Int.MAX_VALUE
                if (newPossibility.length <= cachedLength) {
                    lengthMap[mutations + 1] = newPossibility.length
                    queue.add(newPossibility to mutations + 1)
                }
            }
        }
        return lengthMap[roboCount]!!
    }

    fun part1(inputs: List<String>, roboCount: Int): Long {
        var sum = 0L
        for (i in inputs.indices) {
            val line = inputs[i]
            if (line.isBlank()) continue
            val multiplier = line.substring(0, 3).toLong()
            val minSequenceLength = getMinSequenceLength(line, roboCount)
            val score = minSequenceLength * multiplier
            sum += score
        }
        return sum
    }

    val cache = hashMapOf<Triple<String, Int, Int>, Long>()

    fun minSequenceLength(line: String, depth: Int, limit: Int): Long = cache.getOrPut(Triple(line, depth, limit)) {
        var pos = 'A'
        var sum = 0L
        for (i in line.indices) {
            val target = line[i]
            var min = 0L
            val onPath: (String) -> Unit = { newSequence ->
                val length = if (depth < limit) {
                    minSequenceLength(newSequence, depth + 1, limit)
                } else {
                    newSequence.length.toLong()
                }
                if (min == 0L || length < min) {
                    min = length
                }
            }
            if (depth == 0) {
                getDirectionsPath(keyPad, keyPadX, keyPadY, pos, target, onPath)
            } else {
                getDirectionsPath(dPad, dPadX, dPadY, pos, target, onPath)
            }
            pos = target
            sum += min
        }
        sum
    }

    fun solve(inputs: List<String>, roboCount: Int): Long {
        var sum = 0L
        for (i in inputs.indices) {
            val line = inputs[i]
            if (line.isBlank()) continue
            val multiplier = line.substring(0, 3).toLong()
            val minSequenceLength = minSequenceLength(line, 0, roboCount)
            val score = minSequenceLength * multiplier
            sum += score
        }
        return sum
    }

    val testInput = readTestInput(2024, 21)
    val actualInput = readInput(2024, 21)

    measureTime {
        part1(testInput, 2).println()
        part1(actualInput, 2).println()
    }.println()

    measureTime {
        solve(testInput, 2).println()
        solve(actualInput, 25).println()
    }.println()
}
