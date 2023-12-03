import Day03.part1
import Day03.part2

fun main() {
    val input1 = readInput("Day03_part1")
    println("Part 1 result : ${part1(input1)}")

    val input2 = readInput("Day03_part2")
    println("Part 2 result : ${part2(input2)}")
}

object Day03 {
    fun part1(input: List<String>): Int {
        return input.mapIndexed { lineIndex, line ->
            findSymbols(line)
                .flatMap {
                    val symbolCharIndex = it.range.first
                    input.getAdjacentEnginePartNumbers(lineIndex, symbolCharIndex)
                }
        }.flatten().sum()
    }

    fun part2(input: List<String>): Int {
        return input.mapIndexed { lineIndex, line ->
            findSymbols(line)
                .map { symbol ->
                    val symbolCharIndex = symbol.range.first
                    input.getAdjacentEnginePartNumbers(lineIndex, symbolCharIndex).let {
                        if (it.size > 1) {
                            it.reduce { accumulator, item -> accumulator * item }
                        } else 0
                    }

                }
        }.flatten().sum()
    }
}

private fun List<String>.getAdjacentEnginePartNumbers(lineIndex: Int, charIndex: Int): Set<Int> {
    val adjacentEnginePartNumbers = mutableSetOf<Int>()

    for (currentLineIndex in lineIndex - 1..lineIndex + 1) {
        val adjacentEnginePartNumbersPerLine = mutableSetOf<Int>()

        for (currentCharIndex in charIndex - 1..charIndex + 1) {
            val hasAdjacentNumber = runCatching { this[currentLineIndex][currentCharIndex].isDigit() }
                .getOrElse { false }

            if (hasAdjacentNumber) {
                this.getAdjacentEnginePartNumber(currentLineIndex, currentCharIndex).let {
                    adjacentEnginePartNumbersPerLine.add(it)
                }
            }
        }

        adjacentEnginePartNumbers.addAll(adjacentEnginePartNumbersPerLine)
    }

    return adjacentEnginePartNumbers
}

private fun List<String>.getAdjacentEnginePartNumber(lineIndex: Int, charIndex: Int): Int =
    findNumbers(this[lineIndex])
        .filter { it.range.contains(charIndex) }
        .map { it.value.toInt() }
        .first()

private fun findNumbers(line: String) =
    Regex("(\\d+)").findAll(line).toList()

private fun findSymbols(line: String): List<MatchResult> =
    Regex("[^0-9\\.]").findAll(line).toList()