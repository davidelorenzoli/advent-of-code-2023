import Day06.part1
import Day06.part2

fun main() {
    val input1 = readInput("Day06_part1")
    println("Part 1 result : ${part1(input1)}")

    val input2 = readInput("Day06_part2")
    println("Part 2 result : ${part2(input2)}")
}

object Day06 {

    fun part1(input: List<String>): Long =
        parseTimeDistanceMap(input).map { (raceTime, recordDistance) ->
            computeDifferentWaysYouCouldWin(raceTime, recordDistance)
        }.fold(1) { product: Long, value: Int ->
            product * value
        }

    fun part2(input: List<String>): Long =
        parseTimeDistanceMap(input).map { (raceTime, recordDistance) ->
            computeDifferentWaysYouCouldWin(raceTime, recordDistance)
        }.fold(1) { product: Long, value: Int ->
            product * value
        }

    private fun computeDifferentWaysYouCouldWin(raceTime: Long, recordDistance: Long): Int {
        var differentWaysYouCouldWin = 0

        for (chargingTime in 0..raceTime) {
            val time = raceTime - chargingTime
            val totalDistance = chargingTime * time

            if (totalDistance > recordDistance) {
                differentWaysYouCouldWin++
            }
        }
        return differentWaysYouCouldWin
    }

    private fun parseTimeDistanceMap(input: List<String>): Map<Long, Long> {
        val times = Regex("(\\d+)").findAll(input[0]).map { it.value.toLong() }.toList()
        val distances = Regex("(\\d+)").findAll(input[1]).map { it.value.toLong() }.toList()

        return times.zip(distances).toMap()
    }
}