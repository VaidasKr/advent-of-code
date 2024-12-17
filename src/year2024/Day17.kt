package year2024

import println
import readInput
import readTestInput

fun main() {
    fun longPowOf2(value: Long): Long {
        var result = 1L
        for (i in 0 until value) {
            result *= 2
        }
        return result
    }

    fun comboOperand(operand: Long, registerA: Long, registerB: Long, registerC: Long): Long {
        if (operand in 0..3) return operand
        if (operand == 4L) return registerA
        if (operand == 5L) return registerB
        if (operand == 6L) return registerC
        throw UnsupportedOperationException("unsupported operand $operand")
    }

    fun runProgram(
        program: List<Long>, registerA: Long, registerB: Long, registerC: Long
    ): String {
        var registerA1 = registerA
        var registerB1 = registerB
        var registerC1 = registerC
        val output = ArrayList<Long>()
        var index = 0
        while (index < program.size) {
            val instruction = program[index].toInt()
            val operand = program[index + 1]
            when (instruction) {
                0 -> {
                    registerA1 /= longPowOf2(comboOperand(operand, registerA1, registerB1, registerC1))
                    index += 2
                }

                1 -> {
                    registerB1 = registerB1 xor operand
                    index += 2
                }

                2 -> {
                    registerB1 = comboOperand(operand, registerA1, registerB1, registerC1) % 8
                    index += 2
                }

                3 -> {
                    if (registerA1 == 0L) {
                        index += 2
                    } else {
                        index = operand.toInt()
                    }
                }

                4 -> {
                    registerB1 = registerB1 xor registerC1
                    index += 2
                }

                5 -> {
                    output.add(comboOperand(operand, registerA1, registerB1, registerC1) % 8)
                    index += 2
                }

                6 -> {
                    registerB1 = registerA1 / longPowOf2(comboOperand(operand, registerA1, registerB1, registerC1))
                    index += 2
                }

                7 -> {
                    registerC1 = registerA1 / longPowOf2(comboOperand(operand, registerA1, registerB1, registerC1))
                    index += 2
                }

                else -> throw UnsupportedOperationException("unsupported instruction $instruction")
            }
        }

        return output.joinToString(separator = ",")
    }

    fun part1(inputs: List<String>): String = runProgram(
        program = inputs[4].substring(9).split(',').map { it.toLong() },
        registerA = inputs[0].substring(12).toLong(),
        registerB = inputs[1].substring(12).toLong(),
        registerC = inputs[2].substring(12).toLong()
    )

    fun part2BruteForce(inputs: List<String>): Long {
        var registerB = inputs[1].substring(12).toLong()
        var registerC = inputs[2].substring(12).toLong()
        val program = inputs[4].substring(9).split(',').map { it.toLong() }

        var startingRegisterA = 0L

        while (true) {
            var registerA = startingRegisterA
            var outputIndex = 0
            var index = 0
            while (index < program.size) {
                val instruction = program[index].toInt()
                val operand = program[index + 1]
                when (instruction) {
                    0 -> {
                        registerA /= longPowOf2(comboOperand(operand, registerA, registerB, registerC))
                        index += 2
                    }

                    1 -> {
                        registerB = registerB xor operand
                        index += 2
                    }

                    2 -> {
                        registerB = comboOperand(operand, registerA, registerB, registerC) % 8
                        index += 2
                    }

                    3 -> {
                        if (registerA == 0L) {
                            index += 2
                        } else {
                            index = operand.toInt()
                        }
                    }

                    4 -> {
                        registerB = registerB xor registerC
                        index += 2
                    }

                    5 -> {
                        if (outputIndex >= program.size) {
                            outputIndex = 0
                            break
                        }
                        val value = comboOperand(operand, registerA, registerB, registerC) % 8
                        if (program[outputIndex] == value) {
                            outputIndex++
                        } else {
                            outputIndex = 0
                            break
                        }
                        index += 2
                    }

                    6 -> {
                        registerB = registerA / longPowOf2(comboOperand(operand, registerA, registerB, registerC))
                        index += 2
                    }

                    7 -> {
                        registerC = registerA / longPowOf2(comboOperand(operand, registerA, registerB, registerC))
                        index += 2
                    }

                    else -> throw UnsupportedOperationException("unsupported instruction $instruction")
                }
            }
            if (outputIndex == program.size) {
                return startingRegisterA
            } else {
                startingRegisterA++
            }
        }
    }

    fun part2ForMyInput(inputs: List<String>): Long {
        val program = inputs[4].substring(9).split(',').map { it.toLong() }
        val registerB = inputs[1].substring(12).toLong()
        val registerC = inputs[2].substring(12).toLong()
        var size = 1
        var ongoing = mutableListOf(0L)
        while (size <= program.size) {
            val match = program.takeLast(size).joinToString(",")
            val newOngoing = mutableListOf<Long>()
            ongoing.forEach { num ->
                val range = num * 8
                for (i in 0..7L) {
                    val a = range + i
                    if (match == runProgram(program, a, registerB, registerC)) {
                        newOngoing.add(a)
                    }
                }
            }
            ongoing = newOngoing
            size++
        }
        return ongoing.min()
    }

    val testInput = readTestInput(2024, 17)
    val testInput2 = readTestInput(2024, 17, "2")
    val actualInput = readInput(2024, 17)

    part1(testInput).println()
    part1(actualInput).println()

    part2BruteForce(testInput2).println()
    part2ForMyInput(actualInput).println()
}
