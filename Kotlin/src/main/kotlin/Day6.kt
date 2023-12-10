import java.math.BigInteger

fun main() {
    val input = readFile("../input6.txt")
    //task1(input)
    task2(input)
}

private fun task1(input: List<String>) {
    val durations = input[0].split(":", " ").drop(1).filter { it.isNotBlank()}
    val recordDistances = input[1].split(":", " ").drop(1).filter { it.isNotBlank()}
    val distancesForRace = HashMap<Int, MutableList<Int>>()

    durations.forEachIndexed { raceNr, durationString ->
        val duration = durationString.toInt()
        val distances = mutableListOf<Int>()
        (1..<duration).forEach { timeSpentAccelerating ->
            val raceTime = duration - timeSpentAccelerating
            val distance = timeSpentAccelerating * raceTime
            distances.add(distance)
        }
        distancesForRace[raceNr] = distances
    }

    distancesForRace.values
        .mapIndexed { raceNr, distances ->
        distances
            .count { it > recordDistances[raceNr].toInt() } }
            .reduce { acc, i ->  acc * i }
            .also { println(it) }
}

private fun task2(input: List<String>) {
    val duration = input[0].split(":", " ").drop(1).filter { it.isNotBlank()}.joinToString(separator = "").toBigInteger()
    val recordDistance = input[1].split(":", " ").drop(1).filter { it.isNotBlank()}.joinToString(separator = "").toBigInteger()

    val firstValidEntry = (0..Int.MAX_VALUE).find { (duration - it.toBigInteger()) * it.toBigInteger() > recordDistance }
    firstValidEntry?.let {
        println(duration + BigInteger.ONE - it.times(2).toBigInteger())
    }
}