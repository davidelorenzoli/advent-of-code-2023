import Day04.part1
import Day04.part2
import io.kotest.inspectors.forAllKeys
import kotlin.math.pow

fun main() {
    val input1 = readInput("Day04_part1")
    println("Part 1 result : ${part1(input1)}")

    val input2 = readInput("Day04_part2")
    println("Part 2 result : ${part2(input2)}")
}

object Day04 {

    fun part1(input: List<String>): Int {
        return input.map { line -> parseToCard(line) }
            .map { card ->
                card.potentialWinningNumbers.intersect(card.winningNumbers)
                    .let { actualWinningNumbers ->
                        computeTotalPoints(actualWinningNumbers)
                    }
            }.reduce { accumulator, cardPoints ->
                accumulator + cardPoints
            }
    }

    fun part2(input: List<String>): Int {
        val totalWonCards = mutableListOf<Int>()

        val eligibleCopyOfCardsByCardId = input.map { line -> parseToCard(line) }
            .associate { it.cardId to it.potentialWinningNumbers.intersect(it.winningNumbers).size }

        eligibleCopyOfCardsByCardId.forAllKeys { cardId ->
            totalWonCards.add(cardId)
            computeWonCardIds(cardId, eligibleCopyOfCardsByCardId, totalWonCards)
        }

        return totalWonCards.groupingBy { it }.eachCount().values.sum()
    }
}

private fun computeWonCardIds(
    cardId: Int,
    eligibleCopyOfCardsByCardId: Map<Int, Int>,
    totalWonCards: MutableList<Int>
) {
    val wonCardCount = eligibleCopyOfCardsByCardId[cardId]

    if (wonCardCount == null || wonCardCount == 0) return

    val wonCardIds = (cardId + 1..(cardId + wonCardCount))
    totalWonCards.addAll(wonCardIds)

    wonCardIds.forEach {
        computeWonCardIds(it, eligibleCopyOfCardsByCardId, totalWonCards)
    }
}

private fun parseToCard(card: String): Card {
    var currentCardPart = "CARD_ID"
    var cardId = 0
    val winningNumbers = mutableSetOf<Int>()
    val potentialWinningNumbers = mutableSetOf<Int>()

    Regex("((\\d+)|(:)|(\\|))").findAll(card)
        .toList()
        .forEachIndexed { index, matchResult ->
            val value = matchResult.value

            when (value) {
                ":" -> currentCardPart = "WINNING_NUMBERS"
                "|" -> currentCardPart = "POTENTIALLY_WINNING_NUMBERS"
            }

            if (value.isDigit()) {
                when (currentCardPart) {
                    "CARD_ID" -> cardId = value.toInt()
                    "WINNING_NUMBERS" -> winningNumbers.add(value.toInt())
                    "POTENTIALLY_WINNING_NUMBERS" -> potentialWinningNumbers.add(value.toInt())
                }
            }
        }

    return Card(cardId, winningNumbers, potentialWinningNumbers)
}

private fun computeTotalPoints(it: Set<Int>): Int =
    if (it.isNotEmpty()) {
        2f.pow(it.size - 1).toInt()
    } else {
        0
    }

private fun String.isDigit(): Boolean =
    this.toIntOrNull() != null

data class Card(
    val cardId: Int,
    val winningNumbers: Set<Int>,
    val potentialWinningNumbers: Set<Int>
)