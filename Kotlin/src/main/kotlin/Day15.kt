fun main() {
    val input = readFile("../input15.txt").first()
    task1(input)
    task2(input)
}



private fun task1(input: String) {
    val strings = input.split(',')
    strings.sumOf { hash(it) }.also { println(it) }
}

private fun hash(s: String): Int {
    return s
        .fold(0) { acc, i ->
            (acc + i.code).times(17).rem(256)
        }
}

private fun task2(input: String) {

    // Box Number -> (Lens -> Focal length)
    val boxContents = HashMap<Int, LinkedHashMap<String, Int>>()

    val strings = input.split(',')
    strings.forEach {
        if (it.contains('=')) {
            val (lensLabel, length) = it.split('=')
            val boxNr = hash(lensLabel)
            val entry = boxContents.getOrPut(boxNr) { linkedMapOf() }
            entry[lensLabel] = length.toInt()
        } else if (it.contains('-')) {
            val lensLabel = it.substringBefore('-')
            val boxNr = hash(lensLabel)
            boxContents[boxNr]?.remove(lensLabel)
        }
    }

    var sum = 0
    boxContents.forEach { (boxNr, lenses) ->
        lenses.values.foldIndexed(0) { index, acc, focalLength ->
            acc + ((boxNr+1) * (index + 1) * focalLength)
        }.also { sum += it }
    }
    println(sum)
}
