package year2016

import println
import readInput
import readTestInput

fun main() {
    val testInput = readTestInput(2016, 7)
    val actualInput = readInput(2016, 7)
    part1(testInput).println()
    part1(actualInput).println()
    part2(
        """
                aba[bab]xyz
                xyx[xyx]xyx
                aaa[kek]eke
                zazbz[bzb]cdb
        """.trimIndent().lines()
    ).println()
    part2(actualInput).println()
}

private inline fun String.read(onIn: (String) -> Unit, onOut: (String) -> Unit) {
    var index = 0
    while (index in indices) {
        index = readOut(line = this, index = index, onOut = onOut)
        if (index in indices) {
            index = readIn(line = this, index = index, onIn = onIn)
        }
    }
}

private inline fun readOut(
    line: String,
    index: Int,
    onOut: (String) -> Unit
): Int {
    val end = line.indexOf('[', index)
    return if (end == -1) {
        onOut(line.substring(index))
        end
    } else {
        onOut(line.substring(index, end))
        end + 1
    }
}

private inline fun readIn(
    line: String,
    index: Int,
    onIn: (String) -> Unit
): Int {
    val end = line.indexOf(']', index)
    onIn(line.substring(index, end))
    return end + 1
}

private fun part1(inputs: List<String>): Int = inputs.count { line ->
    hasAbba(line)
}

private fun hasAbba(line: String): Boolean {
    var hasAbba = false
    line.read(
        onIn = {
            if (hasMatchPair(it)) return false
        },
        onOut = {
            hasAbba = hasAbba || hasMatchPair(it)
        }
    )
    return hasAbba
}

private fun hasMatchPair(sequence: String): Boolean {
    if (sequence.length < 4) return false
    for (i in 0 until sequence.length - 3) {
        if (sequence[i] != sequence[i + 1] &&
            sequence[i] == sequence[i + 3] &&
            sequence[i + 1] == sequence[i + 2]
        ) {
            return true
        }
    }
    return false
}

private fun part2(inputs: List<String>): Int = inputs.count { hasSsl2(it) }

private fun hasSsl2(line: String): Boolean {
    val abas = hashSetOf<String>()
    val babs = hashSetOf<String>()
    line.read(
        onIn = {
            if (checkSslMatch(it, babs, abas)) {
                return true
            }
        },
        onOut = {
            if (checkSslMatch(it, abas, babs)) {
                return true
            }
        }
    )
    return false
}

private fun checkSslMatch(part: String, mem: HashSet<String>, check: HashSet<String>): Boolean {
    if (part.length < 3) return false
    for (i in 0 until part.length - 2) {
        if (part[i] == part[i + 2] && part[i] != part[i + 1]) {
            val substring = part.substring(i, i + 2)
            if (substring.reversed() in check) {
                return true
            }
            mem += substring
        }
    }
    return false
}
