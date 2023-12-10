

fun main() {
    val input = readFile("../input3.txt")
    part2(input)
}

private fun part1(
    input: List<String>
) {
    val pattern = """\d+"""
    var sum = 0
    input.forEachIndexed { rowIndex, row ->
        val numbers = Regex(pattern).findAll(row)
        numbers.forEach {
            val startCol = it.range.first
            val endCol = it.range.last
            println("Number sequence for row $rowIndex: $startCol - $endCol")
            if (isSequenceAdjacentToSymbol(startCol, endCol, rowIndex, input)) {
                val numberToBeAdded = row.substring(startCol, endCol+1).toInt()
                println(numberToBeAdded)
                sum += numberToBeAdded
            }
        }
    }
    println(sum)
}

data class PartNumber(
    val id: Int,
    val value: Int
)

private fun part2(
    input: List<String>
) {
    val partNumbers = mutableMapOf<Int, MutableMap<Int, PartNumber>>()
    // Find all part numbers and their value Row -> <Col, PartNumber>
    val pattern = """\d+"""
    var numberId = 0
    input.forEachIndexed { rowIndex, row ->
        val numbers = Regex(pattern).findAll(row)
        numbers.forEach {
            numberId += 1
            val startCol = it.range.first
            val endCol = it.range.last
            if (isSequenceAdjacentToSymbol(startCol, endCol, rowIndex, input)) {
                val numberToBeAdded = row.substring(startCol, endCol+1).toInt()
                val rowEntry = partNumbers.getOrDefault(rowIndex, mutableMapOf())
                val partNumber = PartNumber(numberId, numberToBeAdded)
                (startCol..endCol).forEach {
                    rowEntry[it] = partNumber
                }
                partNumbers[rowIndex] = rowEntry
            }
        }
    }
    // Find unique adjacent part numbers and if there are 2, get the gear ratio
    var sumOfGearRatios = 0
    input.forEachIndexed { rowIndex, row ->
        Regex("""[*]""").findAll(row).forEach {
            val matches = findUniquePartNumbersAdjacent(rowIndex, it.range.start, partNumbers, row.length, input.size)
            if (matches.values.size == 2) {
                val numbers = matches.values.toList()
                sumOfGearRatios += numbers[0] * numbers[1]
            }
        }
    }
    print(sumOfGearRatios)
}

private fun findUniquePartNumbersAdjacent(row: Int, col: Int, partNumbers: MutableMap<Int, MutableMap<Int, PartNumber>>, rowLength: Int, maxRows: Int): MutableMap<Int, Int> {
    val matches = mutableMapOf<Int, Int>()
    println("* found at row $row col $col")
    (maxOf(row-1, 0)..minOf(maxRows - 1, row+1)).forEach { currentRow ->
        if (col > 0) {
            partNumbers[currentRow]?.get(col-1)?.let {
                println("Row: $currentRow Col: ${col-1}")
                matches[it.id] = it.value
            }
        }
        if (col < rowLength - 1) {
            partNumbers[currentRow]?.get(col+1)?.let {
                println("Row: $currentRow Col: ${col+1}")
                matches[it.id] = it.value
            }
        }
        partNumbers[currentRow]?.get(col)?.let {
            println("Row: $currentRow Col: $col")
            matches[it.id] = it.value
        }
        if (matches.size >= 2) return matches
    }
    return matches

}

private fun isSequenceAdjacentToSymbol(start: Int, end: Int, row: Int, input: List<String>): Boolean {
    val lastRow = input.size - 1
    val previousRow = if (row > 0) input[row-1] else null
    val nextRow = if (row < lastRow) input[row+1] else null
    val currentRow = input[row]
    val isSymbol = { c: Char ->  !c.isDigit() && c != '.' }

    if (start > 0 && isSymbol(input[row][start-1])) return true
    if (end+1 < currentRow.length-1 && isSymbol(input[row][end+1])) return true

    val previousRowHasSymbol = previousRow?.let {
        it.substring(maxOf(start-1, 0), minOf(end+2, it.length)).any { c -> isSymbol(c) }
    }

    val nextRowHasSymbol = nextRow?.let {
        it.substring(maxOf(start-1, 0), minOf(end+2, it.length)).any { c -> isSymbol(c) }
    }

    return previousRowHasSymbol == true || nextRowHasSymbol == true
}