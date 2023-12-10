import kotlin.math.pow

fun main() {
    val input = readFile("../input4.txt")
    task1(input)
    task2(input)
}

private fun parseNumbersFromLine(line: String): Pair<Set<String>, Set<String>> {
    val numbers = line.split(':')[1]
    val (myNumbersString, winningNumbersString) = numbers.split('|')
    val myNumbers = myNumbersString.split(' ').filter { it.isNotBlank() }.toSet()
    val winningNumbers = winningNumbersString.split(' ').filter { it.isNotBlank() }.toSet()
    return Pair(myNumbers, winningNumbers)
}

private fun task1(input: List<String>) {
    var sum = 0f
    input.forEach { row ->
        val (myNumbers, winningNumbers) = parseNumbersFromLine(row)
        val nrMatches = myNumbers.intersect(winningNumbers).size
        sum += if(nrMatches > 0) 2f.pow(nrMatches-1) else 0f
    }
    println(sum.toInt())
}

private fun task2(input: List<String>) {
    val lastCard = input.size
    val copies = mutableMapOf<Int, Int>()

    // Initialize 1 card each
    (1..lastCard).forEach {
        copies[it] = 1
    }
    println(copies)

    copies.forEach { (card, nrCopies) ->
        val (myNumbers, winningNumbers) = parseNumbersFromLine(input[card])
        val nrMatches = myNumbers.intersect(winningNumbers).size
        (card+1..card+nrMatches).forEach {
            copies[it] = copies[it]!! + nrCopies
            println("Obtained card ${it} - nr of copies ${copies[it]}")
        }
    }
    println(copies.values.sum())
}