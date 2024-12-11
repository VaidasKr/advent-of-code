package year2024

import println
import readInput

fun main() {
    fun rockCountAfterBlinks(rock: String, blinkLeft: Int, cache: MutableMap<Pair<String, Int>, Long>): Long {
        if (blinkLeft == 0) return 1
        return cache.getOrPut(rock to blinkLeft) {
            if (blinkLeft == 1) {
                if (rock == "0") {
                    1L
                } else {
                    if (rock.length % 2 == 0) 2L else 1L
                }
            } else {
                if (rock == "0") {
                    rockCountAfterBlinks("1", blinkLeft - 1, cache)
                } else {
                    if (rock.length % 2 == 0) {
                        val leftRock = rock.substring(0, rock.length / 2)
                        val rightRock = rock.substring(rock.length / 2).removeLeadingZeros()
                        rockCountAfterBlinks(leftRock, blinkLeft - 1, cache) +
                                rockCountAfterBlinks(rightRock, blinkLeft - 1, cache)
                    } else {
                        rockCountAfterBlinks(rock.toLong().times(2024).toString(), blinkLeft - 1, cache)
                    }
                }
            }
        }
    }

    fun fastBlink(inputs: List<String>, blinks: Int): Long {
        val memory = hashMapOf<Pair<String, Int>, Long>()
        var sum = 0L
        inputs.first().split(' ').forEach {
            sum += rockCountAfterBlinks(it, blinks, memory)
        }
        return sum
    }

    val testInput = listOf("125 17")
    val actualInput = readInput(2024, 11)

    fastBlink(testInput, 6).println()
    fastBlink(testInput, 25).println()
    fastBlink(actualInput, 25).println()
    fastBlink(actualInput, 75).println()
}

private fun String.removeLeadingZeros(): String = if (startsWith('0') && length > 1) {
    val index = indexOfFirst { it != '0' }
    if (index == -1) {
        "0"
    } else {
        substring(index)
    }
} else {
    this
}
