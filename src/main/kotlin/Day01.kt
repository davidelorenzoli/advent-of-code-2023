import Day01.part1
import Day01.part2

fun main() {
    val input1 = readInput("Day01_part1")
    println("Part 1 result : ${part1(input1)}")

    val input2 = readInput("Day01_part2")
    println("Part 2 result : ${part2(input2)}")
}

object Day01 {
    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            computeCalibrationValue(line)
        }

    fun part2(input: List<String>): Int =
        input.sumOf { line ->
            replaceNumberNamesWithDigits(line).let { curatedLine ->
                computeCalibrationValue(curatedLine)
            }
        }

    private fun replaceNumberNamesWithDigits(line: String): String =
        line.mapIndexed { index, char ->
            numberNamesMap.keys.find { numberName ->
                line.substring(index).startsWith(numberName)
            }?.let { numberName ->
                numberNamesMap[numberName].toString()
            } ?: char.toString()
        }.joinToString { it }

    private val numberNamesMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    private fun computeCalibrationValue(line: String): Int {
        val first = line.find { it.isDigit() }
        val last = line.findLast { it.isDigit() }

        return "$first$last".toInt()
    }
}