package year2024

import println
import readInput
import readTestInput
import kotlin.time.measureTime

fun main() {
    fun part1(inputs: List<String>): Long {
        var sum = 0L
        for (i in inputs.indices) {
            val line = inputs[i]
            if (line.isBlank()) continue
            var value = line.toLong()
            repeat(2000) {
                value = ((value * 64) xor value) % 16777216
                value = (value / 32 xor value) % 16777216
                value = (value * 2048 xor value) % 16777216
            }
            sum += value
        }
        return sum
    }

    fun part2(inputs: List<String>): Long {
        val cache = HashMap<Long, Long>()
        val usedSequences = hashSetOf<Long>()
        for (i in inputs.indices) {
            usedSequences.clear()
            val line = inputs[i]
            if (line.isBlank()) continue
            var value = line.toLong()
            var price = value % 10
            var firstDif = 0L
            var secondDif = 0L
            var thirdDif = 0L
            var fourthDif: Long

            for (sI in 0 until 2000) {
                value = ((value * 64) xor value) % 16777216
                value = (value / 32 xor value) % 16777216
                value = (value * 2048 xor value) % 16777216
                val newPrice = value % 10
                fourthDif = thirdDif
                thirdDif = secondDif
                secondDif = firstDif
                firstDif = newPrice - price
                if (sI > 3) {
                    val sequence = firstDif + secondDif * 100 + thirdDif * 10000 + fourthDif * 1000000
                    if (usedSequences.add(sequence)) {
                        val cacheValue = cache[sequence] ?: 0
                        cache[sequence] = cacheValue + newPrice
                    }
                }
                price = newPrice
            }
        }
        return cache.maxOf { (_, value) -> value }
    }

    val testInput = readTestInput(2024, 22)
    val actualInput = readInput(2024, 22)
    part1(testInput).println()
    part1(actualInput).println()
    val testInput2 = readTestInput(2024, 22, "2")
    measureTime {
        part2(testInput2).println()
        part2(actualInput).println()
    }.println()
}
