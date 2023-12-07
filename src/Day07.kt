import kotlin.io.path.Path
import kotlin.io.path.useLines
import kotlin.math.pow

enum class CamelCardType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
}

// I was kinda lazy this day so there's quite some code repetition
fun main() {

    data class HandBid(
        val hand: String,
        val bid: Int
    )

    fun powerFinderPart1(handBid: HandBid): Int {
        val occurrenceMap = mutableMapOf<Char, Int>()
        handBid.hand.forEach { occurrenceMap[it] = occurrenceMap.computeIfAbsent(it) { 0 } + 1 }
        val numberPowerMap = mapOf(
            '2' to 0, '3' to 1, '4' to 2, '5' to 3, '6' to 4,
            '7' to 5, '8' to 6, '9' to 7, 'T' to 8, 'J' to 9,
            'Q' to 10, 'K' to 11, 'A' to 12
        )
        val type = when {
            occurrenceMap.values.max() == 5 -> CamelCardType.FIVE_OF_A_KIND
            occurrenceMap.values.max() == 4 -> CamelCardType.FOUR_OF_A_KIND
            occurrenceMap.values.max() == 3 && occurrenceMap.values.contains(2) -> CamelCardType.FULL_HOUSE
            occurrenceMap.values.max() == 3 -> CamelCardType.THREE_OF_A_KIND
            occurrenceMap.values.count { it == 2 } == 2 -> CamelCardType.TWO_PAIR
            occurrenceMap.values.count { it == 2 } == 1 -> CamelCardType.ONE_PAIR
            else -> CamelCardType.HIGH_CARD
        }
        val power = handBid.hand
                .map { numberPowerMap[it]!! }.joinToString("") { it.toString(16) }
                .toInt(radix = 16)
                .let {
                    it + type.ordinal * 16.0.pow(5.0).toInt()
                }
        return power
    }

    fun powerFinderPart2(handBid: HandBid): Int {
        val occurrenceMap = mutableMapOf<Char, Int>()
        handBid.hand.forEach {
            if(it != 'J') {
                occurrenceMap[it] = occurrenceMap.computeIfAbsent(it) { 0 } + 1
            }
        }
        val numberPowerMap = mapOf(
            'J' to 0, '2' to 1, '3' to 2, '4' to 3, '5' to 4,
            '6' to 5, '7' to 6, '8' to 7, '9' to 8, 'T' to 9,
            'Q' to 10, 'K' to 11, 'A' to 12
        )
        occurrenceMap.maxByOrNull { it.value }?.let {
            occurrenceMap[it.key] = it.value + handBid.hand.count { it == 'J' }
        }
        val type = when {
            occurrenceMap.isEmpty() -> CamelCardType.FIVE_OF_A_KIND
            occurrenceMap.values.max() == 5 -> CamelCardType.FIVE_OF_A_KIND
            occurrenceMap.values.max() == 4 -> CamelCardType.FOUR_OF_A_KIND
            occurrenceMap.values.max() == 3 && occurrenceMap.values.contains(2) -> CamelCardType.FULL_HOUSE
            occurrenceMap.values.max() == 3 -> CamelCardType.THREE_OF_A_KIND
            occurrenceMap.values.count { it == 2 } == 2 -> CamelCardType.TWO_PAIR
            occurrenceMap.values.count { it == 2 } == 1 -> CamelCardType.ONE_PAIR
            else -> CamelCardType.HIGH_CARD
        }
        val power = handBid.hand
            .map { numberPowerMap[it]!! }.joinToString("") { it.toString(16) }
            .toInt(radix = 16)
            .let {
                it + type.ordinal * 16.0.pow(5.0).toInt()
            }
        return power
    }

    val filename = "Day07"

    fun part(part: Int): Int {
        val handBids = Path("src/$filename.txt").useLines { lines ->
            lines.map { line ->
                line.split(' ', limit=2).let {
                    HandBid(it[0], it[1].toInt())
                }
            }.toList()
        }
        return handBids.sortedBy {
            if(part == 1) {
                powerFinderPart1(it)
            } else {
                powerFinderPart2(it)
            }
        }.mapIndexed { index, handBid ->
            (index + 1) * handBid.bid
        }.sum()
    }

    "Part 1 is ${part(1)}".println()
    "Part 2 is ${part(2)}".println()
}