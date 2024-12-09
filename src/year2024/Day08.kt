package year2024

import println
import readInput
import readTestInput

fun main() {
    fun part1(inputs: List<String>): Int {
        val antennaMap = HashMap<Char, ArrayList<Int>>()
        val width = inputs.first().length

        for (y in inputs.indices) {
            val line = inputs[y]
            if (line.isBlank()) break
            for (x in line.indices) {
                val char = line[x]
                if (char != '.') {
                    antennaMap.getOrPut(char) { ArrayList() }.add(x + y * width)
                }
            }
        }

        val positions = HashSet<Int>()

        antennaMap.forEach { (_, antennas) ->
            val indices = antennas.indices
            for (i in indices) {
                val antenna = antennas[i]
                val antennaX = antenna % width
                val antennaY = antenna / width
                for (j in indices) {
                    if (i == j) continue
                    val otherAntenna = antennas[j]
                    val otherAntennaX = otherAntenna % width
                    val otherAntennaY = otherAntenna / width
                    val difX = antennaX - otherAntennaX
                    val difY = antennaY - otherAntennaY
                    val antidoteX = antennaX + difX
                    val antidoteY = antennaY + difY
                    if (antidoteY in inputs.indices && antidoteX in inputs[antidoteY].indices) {
                        positions.add(antidoteX + antidoteY * width)
                    }
                }
            }
        }

        return positions.size
    }

    fun part2(inputs: List<String>): Int {
        val antennaMap = HashMap<Char, ArrayList<Int>>()
        val width = inputs.first().length

        for (y in inputs.indices) {
            val line = inputs[y]
            if (line.isBlank()) break
            for (x in line.indices) {
                val char = line[x]
                if (char != '.') {
                    antennaMap.getOrPut(char) { ArrayList() }.add(x + y * width)
                }
            }
        }

        val positions = HashSet<Int>()

        antennaMap.forEach { (_, antennas) ->
            val indices = antennas.indices
            for (i in indices) {
                val antenna = antennas[i]
                positions.add(antenna)
                val antennaX = antenna % width
                val antennaY = antenna / width
                for (j in indices) {
                    if (i == j) continue
                    val otherAntenna = antennas[j]
                    val otherAntennaX = otherAntenna % width
                    val otherAntennaY = otherAntenna / width
                    val difX = antennaX - otherAntennaX
                    val difY = antennaY - otherAntennaY
                    var antidoteX = antennaX + difX
                    var antidoteY = antennaY + difY
                    while (antidoteY in inputs.indices && antidoteX in inputs[antidoteY].indices) {
                        positions.add(antidoteX + antidoteY * width)
                        antidoteX += difX
                        antidoteY += difY
                    }
                }
            }
        }

        return positions.size
    }

    val testInput = readTestInput(2024, 8)
    val actualInput = readInput(2024, 8)

    part1(testInput).println()
    part1(actualInput).println()

    part2(testInput).println()
    part2(actualInput).println()
}
