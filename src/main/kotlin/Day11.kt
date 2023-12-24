import Day11.part1
import Day11.part2
import kotlin.math.absoluteValue

fun main() {
    val input1 = readInput("Day11_part1")
    println("Part 1 result : ${part1(input1)}")

    val input2 = readInput("Day11_part2")
    println("Part 2 result : ${part2(input2)}")
}

object Day11 {
    fun part1(input: List<String>): Int {
        val galaxies = getGalaxiesCoordinates(input)
        val expandedUniverse = expandGalaxies(galaxies, expansionFactor = 1)
        val galaxyPairs = expandedUniverse.getUniquePairs()

        return galaxyPairs.map { galaxyPair ->
            val distanceX = (galaxyPair.first.first - galaxyPair.second.first).absoluteValue
            val distanceY = (galaxyPair.first.second - galaxyPair.second.second).absoluteValue

            return@map distanceX + distanceY
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val galaxies = getGalaxiesCoordinates(input)
        val expandedUniverse = expandGalaxies(galaxies, expansionFactor = 999_999)
        val galaxyPairs = expandedUniverse.getUniquePairs()

        return galaxyPairs.map { galaxyPair ->
            val distanceX = (galaxyPair.first.first - galaxyPair.second.first).absoluteValue
            val distanceY = (galaxyPair.first.second - galaxyPair.second.second).absoluteValue

            return@map distanceX + distanceY
        }.sum()
    }
}

private fun List<Pair<Int, Int>>.getUniquePairs(): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
    val galaxyPairs = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    this.forEach { coordinateA ->
        this.forEach { coordinateB ->
            val pair1 = Pair(coordinateA, coordinateB)
            val pair2 = Pair(coordinateB, coordinateA)

            if (!galaxyPairs.contains(pair1) && !galaxyPairs.contains(pair2) && coordinateA != coordinateB) {
                galaxyPairs.add(pair1)
            }
        }
    }

    return galaxyPairs
}

private fun expandGalaxies(galaxies: List<Pair<Int, Int>>, expansionFactor: Int): List<Pair<Int, Int>> {
    val emptyRows = galaxies.computeEmptyRows().reversed()
    val emptyColumns = galaxies.computeEmptyColumns().reversed()
    val expandedGalaxies = galaxies.toMutableList()

    emptyRows.forEach { row ->
        expandedGalaxies.mapIndexed { index, galaxy ->
            if (galaxy.first > row) {
                expandedGalaxies[index] = Pair(galaxy.first + expansionFactor, galaxy.second)
            }
        }
    }

    emptyColumns.forEach { column ->
        expandedGalaxies.mapIndexed { index, galaxy ->
            if (galaxy.second > column) {
                expandedGalaxies[index] = Pair(galaxy.first, galaxy.second + expansionFactor)
            }
        }
    }

    return expandedGalaxies
}

private fun List<Pair<Int, Int>>.computeEmptyRows(): MutableList<Int> {
    val emptyRows = mutableListOf<Int>()
    for (row in 0..<this.maxOf { it.first }) {
        if (this.none { it.first == row }) emptyRows.add(row)
    }
    return emptyRows
}

private fun List<Pair<Int, Int>>.computeEmptyColumns(): MutableList<Int> {
    val emptyColumns = mutableListOf<Int>()
    for (column in 0..<this.maxOf { it.second }) {
        if (this.none { it.second == column }) emptyColumns.add(column)
    }
    return emptyColumns
}

private fun getGalaxiesCoordinates(input: List<String>): List<Pair<Int, Int>> =
    input.mapIndexed { rowIndex, line ->
        line.mapIndexedNotNull { columnIndex, char ->
            if (char == '#') Pair(rowIndex, columnIndex) else null
        }
    }.flatten()
