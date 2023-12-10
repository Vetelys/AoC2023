fun main() {
    val input = readFile("../input9.txt")
    task1(input)
    task2(input)
}

private fun task1(input: List<String>) {
    var sum = 0
    input.forEach {history ->
        val sequence = history.split(" ").map { it.toInt() }
        val lastSequence = generateSequenceOfDifferences(sequence)
        sum += lastSequence.last()
    }
    println(sum)
}

private fun task2(input: List<String>) {
    var sum = 0
    input.forEach { history ->
        val sequence = history.split(" ").map { it.toInt() }
        val lastSequence = generateSequenceOfDifferencesBackwards(sequence)
        sum += lastSequence.first()
    }
    println(sum)
}

private fun generateSequenceOfDifferences(list: List<Int>): List<Int> {
    return if (list.all { it == 0 }) {
        list.toMutableList().also { it.add(0) }
    } else {
        val nextSequence = list.mapIndexedNotNull { index, value ->
            list.getOrNull(index+1)?.run {
                this.minus(value)
            }
        }
        val processedSequence = generateSequenceOfDifferences(nextSequence)
        return list.toMutableList().also { it.add(it.last() + processedSequence.last()) }
    }
}

private fun generateSequenceOfDifferencesBackwards(list: List<Int>): List<Int> {
    return if (list.all { it == 0 }) {
        list.toMutableList().also { it.addFirst(0) }
    } else {
        val nextSequence = list.mapIndexedNotNull { index, value ->
            list.getOrNull(index+1)?.run {
                this.minus(value)
            }
        }
        val processedSequence = generateSequenceOfDifferencesBackwards(nextSequence)
        return list.toMutableList().also { it.addFirst(it.first() - processedSequence.first()) }
    }
}