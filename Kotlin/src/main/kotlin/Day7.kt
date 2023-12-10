val handToBid = HashMap<String, Int>()

fun main() {
    val input = readFile("../input7.txt")
    input.forEach {
        val (hand, bid) = it.split(" ")
        handToBid[hand] = bid.toInt()
    }
    task1()
    task2()
}

private val strengthOrder = mapOf('A' to 13, 'K' to 12, 'Q' to 11, 'J' to 10, 'T' to 9, '9' to 8, '8' to 7, '7' to 6, '6' to 5, '5' to 4, '4' to 3, '3' to 2, '2' to 1)
private val strengthOrder2 = mapOf('A' to 13, 'K' to 12, 'Q' to 11, 'T' to 10, '9' to 9, '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2, 'J' to 1)
private val typeOrder = mapOf(Type.FIVE_OF_KIND to 7, Type.FOUR_OF_KIND to 6, Type.FULL_HOUSE to 5, Type.THREE_OF_KIND to 4, Type.TWO_PAIR to 3, Type.ONE_PAIR to 2, Type.HIGH_CARD to 1)

private data class Hand(
    val cards: String,
    val type: Type,
    val value: Int,
)


private enum class Type() {
    FIVE_OF_KIND,
    FOUR_OF_KIND,
    FULL_HOUSE,
    THREE_OF_KIND,
    TWO_PAIR,
    ONE_PAIR,
    HIGH_CARD,
}

private fun task1() {
    val hands = handToBid.keys.map { Hand(cards = it, type = getType(it), value = handToBid.getOrDefault(it, 0)) }
    val sortedByType = hands.sortedBy { typeOrder[it.type] }
    val sortedHands = sortedByType.sortedWith(handStrengthComparator)
    sortedHands.mapIndexed { index, hand -> hand.value * (index + 1) }.reduce { acc, i -> acc + i }.also { println(it) }
}

private fun task2() {
    val hands = handToBid.keys.map { Hand(cards = it, type = getType(it, handleJokers = true), value = handToBid.getOrDefault(it, 0)) }
    val sortedByType = hands.sortedBy { typeOrder[it.type] }
    val sortedHands = sortedByType.sortedWith(handStrengthComparator2)
    sortedHands.mapIndexed { index, hand -> hand.value * (index + 1) }.reduce { acc, i -> acc + i }.also { println(it) }
}

private fun getType(hand: String, handleJokers: Boolean = false) : Type {
    val cards = hand.toList()
    val separatedCards = cards.groupingBy { it }.eachCount().toMutableMap()
    val jokerCount = if (handleJokers) separatedCards.getOrDefault('J', 0) else 0
    if (handleJokers) {
        separatedCards.remove('J')
    }

    return when {
        (separatedCards.any { it.value + jokerCount == 5 } || jokerCount == 5) -> Type.FIVE_OF_KIND
        (separatedCards.any { it.value + jokerCount == 4  }) -> Type.FOUR_OF_KIND
        (separatedCards.count { it.value == 2 } == 2 && jokerCount == 1) -> Type.FULL_HOUSE
        (separatedCards.count { it.value == 2 } == 1 && separatedCards.count { it.value == 3} == 1 ) && jokerCount == 0 -> Type.FULL_HOUSE
        (separatedCards.any { it.value + jokerCount == 3 }) -> Type.THREE_OF_KIND
        (separatedCards.count { it.value == 2 } == 2 - jokerCount) -> Type.TWO_PAIR
        (separatedCards.count { it.value == 2 } == 1 && jokerCount == 0) -> Type.ONE_PAIR
        jokerCount == 1 -> Type.ONE_PAIR
        else -> Type.HIGH_CARD
    }
}

private val handStrengthComparator = Comparator { hand1: Hand, hand2: Hand ->
    val comparisonResult = compareHands(hand1, hand2)
    when {
        hand1.type != hand2.type -> 0
        else -> comparisonResult
    }
}

private fun compareHands(hand1: Hand, hand2: Hand): Int {
    val cards1 = hand1.cards
    val cards2 = hand2.cards
    (0..cards1.length).forEach {
        if (strengthOrder[cards1[it]]!! > strengthOrder[cards2[it]]!!) {
            return 1
        } else if (strengthOrder[cards1[it]]!! < strengthOrder[cards2[it]]!!) {
            return -1
        }
    }
    return 0
}

private val handStrengthComparator2 = Comparator { hand1: Hand, hand2: Hand ->
    val comparisonResult = compareHands2(hand1, hand2)
    when {
        hand1.type != hand2.type -> 0
        else -> comparisonResult
    }
}

private fun compareHands2(hand1: Hand, hand2: Hand): Int {
    val cards1 = hand1.cards
    val cards2 = hand2.cards
    (0..cards1.length).forEach {
        if (strengthOrder2[cards1[it]]!! > strengthOrder2[cards2[it]]!!) {
            return 1
        } else if (strengthOrder2[cards1[it]]!! < strengthOrder2[cards2[it]]!!) {
            return -1
        }
    }
    return 0
}
