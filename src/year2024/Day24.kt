package year2024

import println
import readInput
import readTestInput

fun main() {
    fun compute(
        output: String,
        opMap: HashMap<String, Triple<String, String, String>>,
        values: HashMap<String, Int>,
        switch: Map<String, String> = emptyMap()
    ): Int = values.getOrPut(output) {
        val switched = switch.getOrElse(output) { output }
        val (first, second, op) = opMap[switched]!!
        val firstValue = compute(first, opMap, values, switch)
        val secondValue = compute(second, opMap, values, switch)
        when (op) {
            "AND" -> firstValue and secondValue
            "OR" -> firstValue or secondValue
            "XOR" -> firstValue xor secondValue
            else -> {
                println("unsopported")
                0
            }
        }
    }

    fun part1(inputs: List<String>): Long {
        val values = hashMapOf<String, Int>()
        val blank = inputs.indexOf("")
        for (i in 0 until blank) {
            val line = inputs[i]
            val (key, value) = line.split(": ")
            values[key] = value.toInt()
        }
        val opMap = hashMapOf<String, Triple<String, String, String>>()
        for (i in blank + 1 until inputs.size) {
            val line = inputs[i]
            if (line.isBlank()) continue
            val (first, op, second, _, result) = line.split(' ')
            opMap[result] = Triple(first, second, op)
        }

        return opMap.asSequence()
            .filter { it.key.startsWith('z') }
            .sortedByDescending { it.key }
            .map { (key, _) -> compute(key, opMap, values).toString() }
            .joinToString("")
            .toLong(2)
    }

    fun part2(inputs: List<String>): Long {
        val values = hashMapOf<String, Int>()
        val blank = inputs.indexOf("")
        var maxX = 0
        for (i in 0 until blank) {
            val line = inputs[i]
            val (key, value) = line.split(": ")
            if (key.startsWith('x')) {
                val num = key.drop(1).toInt()
                if (num > maxX) {
                    maxX = num
                }
            }
            values[key] = value.toInt()
        }
        val opMap = hashMapOf<String, Triple<String, String, String>>()
        for (i in blank + 1 until inputs.size) {
            val line = inputs[i]
            if (line.isBlank()) continue
            val (first, op, second, _, result) = line.split(' ')
            opMap[result] = Triple(first, second, op)
        }

        val zKeys = opMap.keys.toMutableList()
        zKeys.retainAll { it.startsWith('z') }
        zKeys.sort()

        val xyRange = 0..maxX

        val initialValues = HashMap(values)
        initialValues.replaceAll { _, _ -> 0 }

        var xyMultiplier = 1L

        val resultArray = CharArray(maxX + 2)

        var max = 0L
        var hasZeros = false

        val pairs = listOf("z10" to "mkk", "z14" to "qbw", "z34" to "wcb", "wjb" to "cvp")
        pairs.flatMap { it.toList() }.sorted().joinToString(",").println()

        val replaceMap = buildMap {
            for (i in pairs.indices) {
                val (f, s) = pairs[i]
                put(f, s)
                put(s, f)
            }
        }

        for (upI in xyRange) {
            for (x in 0..1) {
                for (y in 0..1) {
                    if (x == 0 && y == 0) {
                        if (hasZeros) continue
                        hasZeros = true
                    }
                    val testValues = HashMap(initialValues)
                    val num = if (upI >= 10) upI.toString() else "0$upI"
                    testValues["x$num"] = x
                    testValues["y$num"] = y
                    var index = 0
                    var multiplier = 1L
                    var sum = 0L
                    for (i in zKeys.indices) {
                        val result = compute(zKeys[i], opMap, testValues, replaceMap)
                        sum += result * multiplier
                        multiplier *= 2
                        resultArray[index] = result.digitToChar()
                        index++
                    }
                    resultArray.reverse()
                    val testExpected = x * xyMultiplier + y * xyMultiplier
                    if (sum != testExpected) {
                        val string = String(resultArray)
                        println("$num x $x y $y $string")
                        println("should be  ${testExpected.toString(2).padStart(string.length, '0')}")
                    }
                }
            }
            max += xyMultiplier
            xyMultiplier *= 2
        }
        max += xyMultiplier
        max -= 1

        initialValues.replaceAll { _, _ -> 1 }
        xyMultiplier = 1L

        var hasOnes = false

        for (upI in xyRange) {
            for (x in 0..1) {
                for (y in 0..1) {
                    if (x == 1 && y == 1) {
                        if (hasOnes) continue
                        hasOnes = true
                    }
                    val testValues = HashMap(initialValues)
                    val num = if (upI >= 10) upI.toString() else "0$upI"
                    testValues["x$num"] = x
                    testValues["y$num"] = y
                    var index = 0
                    var multiplier = 1L
                    var sum = 0L
                    for (i in zKeys.indices) {
                        val result = compute(zKeys[i], opMap, testValues, replaceMap)
                        sum += result * multiplier
                        multiplier *= 2
                        resultArray[index] = result.digitToChar()
                        index++
                    }
                    resultArray.reverse()
                    var testExpected = max
                    if (x == 0) {
                        testExpected -= xyMultiplier
                    }
                    if (y == 0) {
                        testExpected -= xyMultiplier
                    }
                    if (sum != testExpected) {
                        val string = String(resultArray)
                        println("$num x $x y $y $string")
                        println("should be  ${testExpected.toString(2).padStart(string.length, '0')}")
                    }
                }
            }
            xyMultiplier *= 2
        }

        var index = 0
        var multiplier = 1L
        var sum = 0L
        for (i in zKeys.indices) {
            val result = compute(zKeys[i], opMap, values, replaceMap)
            sum += result * multiplier
            multiplier *= 2
            resultArray[index] = result.digitToChar()
            index++
        }

        val xSum = values.keys.asSequence()
            .filter { it.startsWith('x') }
            .sortedByDescending { it }
            .fold(0L) { xSum, next ->
                xSum * 2 + values[next]!!
            }

        val ySum = values.keys.asSequence()
            .filter { it.startsWith('y') }
            .sortedByDescending { it }
            .fold(0L) { ySum, next ->
                ySum * 2 + values[next]!!
            }

        val zExpected = xSum + ySum
        if (zExpected != sum) {
            println("expected ${zExpected.toString(2)}")
            println("actual   ${sum.toString(2)}")
        }

        return 0
    }

    val testInput1 = readTestInput(2024, 24, "1")
    val testInput2 = readTestInput(2024, 24, "2")
    val actualInput = readInput(2024, 24)

    part1(testInput1).println()
    part1(testInput2).println()
    part1(actualInput).println()
//
//    part2(testInput1).println()
    part2(actualInput).println()
}

// a,b,c,d,e,f
// ab, cd, ef
// ab, ce, df
// ab, cf, de
// ac, bd, ef
// ac, be, df
// ac, bf, de
// ad, bc, ef
// ad, be, cf
// ad, bf, ce
// ae, bc, df
// ae, bd, cf
// ae, bf, cd
// af, bc, de
// af, bd, ce
// af, be, cd
