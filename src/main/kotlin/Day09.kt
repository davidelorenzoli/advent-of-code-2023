import Day09.part1
import Day09.part2

fun main() {
    val input1 = readInput("Day09_part1")
    println("Part 1 result : ${part1(input1)}")

    val input2 = readInput("Day09_part2")
    println("Part 2 result : ${part2(input2)}")
}

object Day09 {
    fun part1(input: List<String>): Int {
        val predictedValues = input.map { line ->
            val history = parseToHistory(line)
            val reductions = computeReductions(history)

            return@map computePredictedValueForwards(reductions)
        }

        return predictedValues.sum()
    }

    fun part2(input: List<String>): Int {
        val predictedValues = input.map { line ->
            val history = parseToHistory(line)
            val reductions = computeReductions(history)

            return@map computePredictedValueBackwards(reductions)
        }

        return predictedValues.sum()
    }

    private fun computePredictedValueForwards(reductions: List<List<Int>>): Int {
        var predictedValue = 0

        for (index in reductions.indices.last downTo reductions.indices.first) {
            predictedValue += if (reductions[index].isNotEmpty()) {
                reductions[index].first()
            } else {
                0
            }
        }
        return predictedValue
    }

    private fun computePredictedValueBackwards(reductions: List<List<Int>>): Int {
        var predictedValue = 0

        for (index in reductions.indices.last downTo reductions.indices.first) {
            predictedValue = if (reductions[index].isNotEmpty()) {
                reductions[index].first() - predictedValue
            } else {
                0
            }
        }
        return predictedValue
    }

    private fun parseToHistory(line: String): List<Int> =
        line.split(" ").map { it.toInt() }.toList()

    private fun computeReductions(history: List<Int>): List<List<Int>> {
        val reductionList = mutableListOf(history)
        var historyToBeReduced = history

        do {
            historyToBeReduced = historyToBeReduced.mapIndexedNotNull { index, _ ->
                if (!historyToBeReduced.indices.contains(index + 1)) {
                    return@mapIndexedNotNull null
                }

                return@mapIndexedNotNull (historyToBeReduced[index + 1] - historyToBeReduced[index])
            }.also {
                reductionList.add(it)
            }
        } while (!historyToBeReduced.all { it == 0 })

        return reductionList
    }
}
