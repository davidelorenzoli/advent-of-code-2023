import Day02.part1
import Day02.part2

fun main() {
    val input1 = readInput("Day02_part1")
    println("Part 1 result : ${part1(input1)}")

    val input2 = readInput("Day02_part2")
    println("Part 2 result : ${part2(input2)}")
}

object Day02 {
    fun part1(input: List<String>): Int {
        val targetRed = 12
        val targetGreen = 13
        val targetBlue = 14
        var gameIdSum = 0
        val matcher = Regex("(?<blue>\\d+) blue|(?<red>\\d+) red|(?<green>\\d+) green")

        input.forEachIndexed { index, line ->
            val gameId = index + 1
            var maxRed = 0
            var maxGreen = 0
            var maxBlue = 0

            matcher.findAll(line).forEach { matchResult ->
                with(matchResult) {
                    groups["red"]?.value?.let { if (it.toInt() > maxRed) maxRed = it.toInt() }
                    groups["green"]?.value?.let { if (it.toInt() > maxGreen) maxGreen = it.toInt() }
                    groups["blue"]?.value?.let { if (it.toInt() > maxBlue) maxBlue = it.toInt() }
                }
            }

            if (maxRed <= targetRed && maxGreen <= targetGreen && maxBlue <= targetBlue) {
                println("Game id: $gameId")
                gameIdSum += gameId
            }
        }

        return gameIdSum
    }

    fun part2(input: List<String>): Int {
        var sumOfPowers = 0
        val matcher = Regex("(?<blue>\\d+) blue|(?<red>\\d+) red|(?<green>\\d+) green")

        input.forEach { line ->
            var maxRed = 0
            var maxGreen = 0
            var maxBlue = 0

            matcher.findAll(line).forEach { matchResult ->
                with(matchResult) {
                    groups["red"]?.value?.let { if (it.toInt() > maxRed) maxRed = it.toInt() }
                    groups["green"]?.value?.let { if (it.toInt() > maxGreen) maxGreen = it.toInt() }
                    groups["blue"]?.value?.let { if (it.toInt() > maxBlue) maxBlue = it.toInt() }
                }
            }

            sumOfPowers += maxRed * maxGreen * maxBlue
        }

        return sumOfPowers
    }
}