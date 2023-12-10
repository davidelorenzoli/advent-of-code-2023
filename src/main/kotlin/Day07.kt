import Day07.part1
import Day07.part2
import HandType.FIVE_OF_A_KIND
import HandType.FOUR_OF_A_KIND
import HandType.FULL_HOUSE
import HandType.HIGH_CARD
import HandType.ONE_PAIR
import HandType.THREE_OF_A_KIND
import HandType.TWO_PAIR
import java.util.function.Function

fun main() {
    val input1 = readInput("Day07_part1")
    println("Part 1 result : ${part1(input1)}")

    val input2 = readInput("Day07_part2")
    println("Part 2 result : ${part2(input2)}")
}

object Day07 {
    fun part1(input: List<String>): Int {
        val handsByTypeMap = input.map { line ->
            val cards = line.split(" ")[0]
            val bid = line.split(" ")[1].toInt()
            val handType = cards.computeHandType()

            Hand(cards, handType, bid, CardStrengthCalculator())
        }.groupBy { it.handType }

        val handsSortedByCardStrength = getHandsSortedByCardStrength(handsByTypeMap)

        return handsSortedByCardStrength.mapIndexed { index, hand ->
            val rank = index + 1
            rank * hand.bid
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val handsByTypeMap = input.map { line ->
            val cards = line.split(" ")[0]
            val bid = line.split(" ")[1].toInt()
            val handType = cards.computeHandType(jokerCard = 'J')

            Hand(cards, handType, bid, CardStrengthCalculator(jokerCardStrengthOverride = 1))
        }.groupBy { it.handType }

        val handsSortedByCardStrength = getHandsSortedByCardStrength(handsByTypeMap)

        return handsSortedByCardStrength.mapIndexed { index, hand ->
            val rank = index + 1
            (rank * hand.bid)
        }.sum()
    }
}

private fun getHandsSortedByCardStrength(handsByTypeMap: Map<HandType, List<Hand>>): List<Hand> {
    val handsSortedByTypeStrength = handsByTypeMap
        .toSortedMap { handType1, handType2 -> handType1.strength - handType2.strength }

    return handsSortedByTypeStrength.values
        .flatMap { hands -> hands.sorted() }
}

private fun String.computeHandType(jokerCard: Char? = null): HandType {
    val charCountMap = mutableMapOf<Char, Int>()

    this.forEach { char ->
        charCountMap.compute(char) { _, count -> count?.inc() ?: 1 }
    }

    jokerCard?.let {
        this.count { it == jokerCard }.let { jokerCardCount ->
            charCountMap[jokerCard] = 0
            val mostFrequentCard = charCountMap.maxBy { it.value }
            charCountMap.compute(mostFrequentCard.key) { _, value ->
                value?.plus(jokerCardCount)
            }
        }
    }

    return when {
        charCountMap.values.contains(5) -> FIVE_OF_A_KIND
        charCountMap.values.contains(4) -> FOUR_OF_A_KIND
        charCountMap.values.contains(3) && charCountMap.values.contains(2) -> FULL_HOUSE
        charCountMap.values.contains(3) -> THREE_OF_A_KIND
        charCountMap.values.filter { it == 2 }.size == 2 -> TWO_PAIR
        charCountMap.values.contains(2) -> ONE_PAIR
        else -> HIGH_CARD
    }
}

enum class HandType(val strength: Int) {
    FIVE_OF_A_KIND(6),
    FOUR_OF_A_KIND(5),
    FULL_HOUSE(4),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2),
    ONE_PAIR(1),
    HIGH_CARD(0),
}

data class Hand(
    val cards: String,
    val handType: HandType,
    val bid: Int,
    val cardStrengthCalculator: Function<Char, Int>
) : Comparable<Hand> {

    override fun compareTo(other: Hand): Int {
        for (index in this.cards.indices) {
            if (this.cards[index] != other.cards[index]) {
                return cardStrengthCalculator.apply(this.cards[index]) - cardStrengthCalculator.apply(other.cards[index])
            }
        }

        return 0
    }
}

private class CardStrengthCalculator(private val jokerCardStrengthOverride: Int? = null) : Function<Char, Int> {
    override fun apply(card: Char): Int =
        when (card) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> jokerCardStrengthOverride ?: 11
            'T' -> 10
            '9' -> 9
            '8' -> 8
            '7' -> 7
            '6' -> 6
            '5' -> 5
            '4' -> 4
            '3' -> 3
            '2' -> 2
            else -> 0
        }
}
