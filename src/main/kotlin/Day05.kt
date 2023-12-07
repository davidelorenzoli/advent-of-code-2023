import Day05.part1
import Day05.part2

fun main() {
    val input1 = readInput("Day05_part1")
    println("Part 1 result : ${part1(input1)}")

    val input2 = readInput("Day05_part2")
    println("Part 2 result : ${part2(input2)}")
}

object Day05 {

    fun part1(input: List<String>): Long {
        val seeds = parseSeeds(input)
        val destinationToSourceMaps = parseDestinationToSourceMaps(input)

        var closestLocation = Long.MAX_VALUE

        seeds.forEach { seed ->
            val currentClosestLocation = destinationToSourceMaps.findClosestLocation(seed)

            if (currentClosestLocation < closestLocation) {
                closestLocation = currentClosestLocation
            }
        }

        return closestLocation
    }

    fun part2(input: List<String>): Long {
        val seedRanges = parseSeedRanges(input)
        val destinationToSourceMaps = parseDestinationToSourceMaps(input)

        var closestLocation = Long.MAX_VALUE

        seedRanges.forEach { seedRange ->
            seedRange.forEach { seed ->
                val currentClosestLocation = destinationToSourceMaps.findClosestLocation(seed)

                if (currentClosestLocation < closestLocation) {
                    closestLocation = currentClosestLocation
                }
            }
        }

        return closestLocation
    }
}

private fun List<Map<LongRange, LongRange>>.findClosestLocation(seed: Long): Long {
    var closestLocation = Long.MAX_VALUE
    var currentDestination = seed

    this.forEach { destinationToSourceMap ->
        val currentSource = currentDestination
        currentDestination = destinationToSourceMap.getDestination(currentSource)
    }

    if (currentDestination < closestLocation) {
        closestLocation = currentDestination
    }

    return closestLocation
}

private fun List<String>.parseSourceAndDestinationCategories(index: Int): Map<LongRange, LongRange> {
    val rangeMap = mutableMapOf<LongRange, LongRange>()
    var currentIndex = index + 1

    while (currentIndex <= this.lastIndex && this[currentIndex].isNotBlank()) {
        val (destinationRange, sourceRange) = Regex("(\\d+)").findAll(this[currentIndex])
            .map { it.value.toLong() }
            .toList()
            .let {
                val destinationRangeStart = it[0]
                val sourceRangeStart = it[1]
                val rangeLength = it[2] - 1
                Pair(
                    LongRange(destinationRangeStart, destinationRangeStart + rangeLength),
                    LongRange(sourceRangeStart, sourceRangeStart + rangeLength)
                )
            }

        rangeMap[destinationRange] = sourceRange

        currentIndex++
    }

    return rangeMap
}

private fun Map<LongRange, LongRange>.getDestination(source: Long): Long {
    this.forEach { (destinationRange, sourceRange) ->
        if (sourceRange.contains(source)) {
            val sourceIndex = (source - sourceRange.first).toInt()
            val destinationValue = destinationRange.first + sourceIndex

            return destinationValue
        }
    }

    return source
}

private fun parseSeeds(input: List<String>): List<Long> =
    Regex("(\\d+)").findAll(input[0])
        .map { it.value.toLong() }
        .toList()

private fun parseSeedRanges(input: List<String>): List<LongRange> =
    Regex("(\\d+ \\d+)").findAll(input[0])
        .map {
            it.value.split(" ").let {
                val seedRangeStart = it[0].toLong()
                val seedRangeLength = seedRangeStart + it[1].toLong() - 1
                LongRange(seedRangeStart, seedRangeLength)
            }
        }.toList()

private fun parseDestinationToSourceMaps(input: List<String>): List<Map<LongRange, LongRange>> {
    val destinationToSourceMaps = mutableListOf<Map<LongRange, LongRange>>()

    var index = 0
    do {
        if (input[index].startsWith("seed-to-soil")) {
            destinationToSourceMaps.add(input.parseSourceAndDestinationCategories(index))
        }
        if (input[index].startsWith("soil-to-fertilizer")) {
            destinationToSourceMaps.add(input.parseSourceAndDestinationCategories(index))
        }
        if (input[index].startsWith("fertilizer-to-water")) {
            destinationToSourceMaps.add(input.parseSourceAndDestinationCategories(index))
        }
        if (input[index].startsWith("water-to-light")) {
            destinationToSourceMaps.add(input.parseSourceAndDestinationCategories(index))
        }
        if (input[index].startsWith("light-to-temperature")) {
            destinationToSourceMaps.add(input.parseSourceAndDestinationCategories(index))
        }
        if (input[index].startsWith("temperature-to-humidity")) {
            destinationToSourceMaps.add(input.parseSourceAndDestinationCategories(index))
        }
        if (input[index].startsWith("humidity-to-location")) {
            destinationToSourceMaps.add(input.parseSourceAndDestinationCategories(index))
        }
    } while (++index <= input.lastIndex)

    return destinationToSourceMaps
}