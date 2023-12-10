import Day08.part1
import Day08.part2

fun main() {
    val input1 = readInput("Day08_part1")
    println("Part 1 result : ${part1(input1)}")

    val input2 = readInput("Day08_part2")
    println("Part 2 result : ${part2(input2)}")
}

object Day08 {
    fun part1(input: List<String>): Int {
        val instructions = parseInstructions(input)
        val networkNodesLookupMap = parseLookupMap(input)

        var currentNode = "AAA"
        var stepCount = 0

        do {
            stepCount++
            val direction = instructions.nextDirection()
            currentNode = networkNodesLookupMap[currentNode]!!.nextNode(direction)
        } while (currentNode != "ZZZ")

        return stepCount
    }

    fun part2(input: List<String>): Long {
        val instructions = parseInstructions(input)
        val networkNodesLookupMap = parseLookupMap(input)

        val startNodes = networkNodesLookupMap.keys.filter { it.endsWith("A") }

        val steps = startNodes.map { startNode ->
            var currentNode = startNode
            var stepCount = 0L

            do {
                stepCount++
                val direction = instructions.nextDirection()
                currentNode = networkNodesLookupMap[currentNode]!!.nextNode(direction)
            } while (!currentNode.endsWith("Z"))

            return@map stepCount
        }

        return Math.lcm(steps)
    }

    private fun parseLookupMap(input: List<String>): Map<String, Pair<String, String>> =
        input.filterIndexed { index, _ -> index > 0 }
            .mapNotNull { line ->
                Regex("(\\w{3})").findAll(line)
                    .toList()
                    .takeIf { it.isNotEmpty() }
                    ?.let { it[0].value to Pair(it[1].value, it[2].value) }
            }.toMap()

    private fun parseInstructions(input: List<String>): Instructions {
        val instructions = Regex("(\\w+)")
            .findAll(input[0])
            .first().value

        return Instructions(instructions)
    }
}

private fun Pair<String, String>.nextNode(direction: Char): String =
    when (direction) {
        'L' -> this.first
        'R' -> this.second
        else -> ""
    }

data class Instructions(
    val instructions: String
) {
    private var currentIndex = 0

    fun nextDirection(): Char {
        if (!instructions.indices.contains(currentIndex)) {
            currentIndex = 0
        }
        return instructions[currentIndex]
            .also { currentIndex++ }
    }
}

private object Math {
    fun gcd(a: Long, b: Long): Long {
        var a = a
        var b = b
        while (b > 0) {
            val temp = b
            b = a % b
            a = temp
        }
        return a
    }

    fun lcm(a: Long, b: Long): Long {
        return a * (b / gcd(a, b))
    }

    fun lcm(input: List<Long>): Long {
        var result = input[0]
        for (i in 1 until input.size) result = lcm(result, input[i])
        return result
    }
}
