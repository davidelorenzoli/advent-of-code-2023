import Day10.part1
import Day10.part2
import kotlin.math.absoluteValue

fun main() {
    val input1 = readInput("Day10_part1")
    println("Part 1 result : ${part1(input1)}")

    val input2 = readInput("Day10_part2")
    println("Part 2 result : ${part2(input2)}")
}

object Day10 {
    fun part1(input: List<String>): Int {
        val field = Field(input)
        val startingPosition = field.getStartingPosition()
        var nextPosition: Pair<Int, Int>? = startingPosition
        val visitedPositions = mutableListOf<Pair<Int, Int>>()

        do {
            nextPosition = nextPosition?.let {
                visitedPositions.add(it)
                field.getNextPossiblePositions(it)
                    .firstOrNull { !visitedPositions.contains(it) }
            }
        } while (nextPosition != null)

        return computeFarthestPoint(visitedPositions.size)
    }

    fun part2(input: List<String>): Int {
        val field = Field(input)
        val startingPosition = field.getStartingPosition()
        var nextPosition: Pair<Int, Int>? = startingPosition
        val visitedPositions = mutableListOf<Pair<Int, Int>>()

        do {
            nextPosition = nextPosition?.let {
                visitedPositions.add(it)
                field.getNextPossiblePositions(it)
                    .firstOrNull { !visitedPositions.contains(it) }
            }
        } while (nextPosition != null)

        return computeInternalPoints(visitedPositions)
    }
}

private fun Pair<Int, Int>.north(): Pair<Int, Int> = Pair(this.first - 1, this.second)
private fun Pair<Int, Int>.south(): Pair<Int, Int> = Pair(this.first + 1, this.second)
private fun Pair<Int, Int>.east(): Pair<Int, Int> = Pair(this.first, this.second + 1)
private fun Pair<Int, Int>.west(): Pair<Int, Int> = Pair(this.first, this.second - 1)

private val directions = listOf("north", "south", "east", "west")

private val pipeToDirectionMap = mapOf(
    '|' to listOf("north", "south"),
    '-' to listOf("east", "west"),
    'L' to listOf("north", "east"),
    'J' to listOf("north", "west"),
    '7' to listOf("south", "west"),
    'F' to listOf("south", "east")
)

private val directionToPipeMap = mapOf(
    "north" to listOf('|', '7', 'F'),
    "south" to listOf('|', 'L', 'J'),
    "east" to listOf('-', 'J', '7'),
    "west" to listOf('-', 'L', 'F')
)

private fun Field.inferPipeType(position: Pair<Int, Int>): Char? {
    val currentPipe = getPipe(position)

    return if (currentPipe == 'S') {
        val neighbouringPipesByDirection = getNeighbouringPipes(position)

        val nextDirection = directions.mapNotNull { direction ->
            directionToPipeMap[direction]?.let { pipes ->
                if (pipes.contains(neighbouringPipesByDirection[direction])) direction else null
            }
        }

        pipeToDirectionMap.mapNotNull {
            if (it.value.containsAll(nextDirection)) it.key else null
        }.first()
    } else {
        currentPipe
    }
}

private fun Field.getNextPossiblePositions(fromPosition: Pair<Int, Int>): List<Pair<Int, Int>> {
    val currentPipe = getPipe(fromPosition).let {
        if (it == 'S') inferPipeType(fromPosition) else it
    }

    val neighbouringPipes = getNeighbouringPipes(fromPosition)

    return directions.mapNotNull {
        val canMove = pipeToDirectionMap[currentPipe]?.contains(it) ?: false && directionToPipeMap[it]?.contains(neighbouringPipes[it]) ?: false

        if (canMove) {
            when (it) {
                "north" -> fromPosition.north()
                "south" -> fromPosition.south()
                "east" -> fromPosition.east()
                "west" -> fromPosition.west()
                else -> throw IllegalArgumentException(it)
            }
        } else {
            null
        }
    }
}

private fun computeFarthestPoint(count: Int): Int =
    when {
        count % 2 == 0 -> count / 2
        else -> (count + 1) / 2
    }

private fun computeArea(points: List<Pair<Int, Int>>): Int =
    List(points.size) { index ->
        val nextIndex = if (index + 1 in points.indices) index + 1 else 0
        (points[index].first + points[nextIndex].first) * (points[index].second - points[nextIndex].second)
    }.sum().div(2).absoluteValue

private fun computeInternalPoints(visitedPositions: MutableList<Pair<Int, Int>>): Int {
    val area = computeArea(visitedPositions)
    return area - visitedPositions.size / 2 + 1
}

data class Field(private val input: List<String>) {
    private val field = input.map {
        it.toCharArray()
    }.toList()

    fun getPipe(position: Pair<Int, Int>): Char? {
        if (!field.indices.contains(position.first)) return null
        if (!field[position.first].indices.contains(position.second)) return null

        return field[position.first][position.second]
    }

    fun getStartingPosition(): Pair<Int, Int> {
        for (row in field.indices) {
            for (column in field[row].indices) {
                if (field[row][column] == 'S') {
                    return Pair(row, column)
                }
            }
        }

        throw java.lang.IllegalArgumentException("Symbol 'S' not found")
    }

    fun getNeighbouringPipes(position: Pair<Int, Int>) =
        mapOf(
            "north" to getPipe(position.north()),
            "south" to getPipe(position.south()),
            "east" to getPipe(position.east()),
            "west" to getPipe(position.west())
        )
}
