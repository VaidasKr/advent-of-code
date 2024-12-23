package year2024

import println
import readInput
import readTestInput
import kotlin.time.measureTime

fun main() {
    fun getConnections(inputs: List<String>): HashMap<String, HashSet<String>> {
        val connections = hashMapOf<String, HashSet<String>>()
        for (i in inputs.indices) {
            val line = inputs[i]
            if (line.isBlank()) continue
            val (first, second) = line.split('-')
            connections.getOrPut(first) { HashSet() }.add(second)
            connections.getOrPut(second) { HashSet() }.add(first)
        }
        return connections
    }

    fun part1(inputs: List<String>): Int {
        val connections = getConnections(inputs)

        val connectedSets = HashSet<Set<String>>()

        connections.forEach { (node, connected) ->
            connected.forEach { firstConnection ->
                connections[firstConnection]!!.forEach { secondConnection ->
                    if (secondConnection in connected) {
                        connectedSets.add(setOf(node, firstConnection, secondConnection))
                    }
                }
            }
        }

        return connectedSets.filter { set -> set.any { node -> node.startsWith("t") } }.size
    }

    fun part2(inputs: List<String>): String {
        val connections = getConnections(inputs)

        var biggestGroup = setOf("")

        connections.forEach { (startNode, startConnections) ->
            var ongoing: Set<Pair<Set<String>, Set<String>>> =
                setOf(setOf(startNode) to startConnections)
            while (ongoing.isNotEmpty()) {
                val newOngoing = hashSetOf<Pair<Set<String>, Set<String>>>()
                for (nodesAndSharedConnections in ongoing) {
                    val (nodes, sharedConnections) = nodesAndSharedConnections
                    sharedConnections.forEach { sharedNode ->
                        val newSharedConnections = connections[sharedNode]!!.filterTo(hashSetOf()) {
                            it in sharedConnections
                        }
                        val connectedNodes = nodes + sharedNode
                        if (newSharedConnections.isNotEmpty()) {
                            newOngoing.add(connectedNodes to newSharedConnections)
                        } else if (connectedNodes.size > biggestGroup.size) {
                            biggestGroup = connectedNodes
                        }
                    }
                }
                ongoing = newOngoing
            }
        }

        return biggestGroup.sorted().joinToString(",")
    }

    val testInput = readTestInput(2024, 23)
    val actualInput = readInput(2024, 23)

    part1(testInput).println()
    part1(actualInput).println()

    measureTime {
        part2(testInput).println()
        part2(actualInput).println()
    }.println()
}
