fun main() {
    val input = readFile("../input13.txt")
    val grids = input.flatMapIndexed { index: Int, s: String ->
        when {
            index == 0 || index == input.lastIndex -> listOf(index)
            s.isBlank() -> listOf(index -1, index + 1)
            else -> emptyList()
        }
    }.windowed(size = 2, step = 2) { (from, to) -> input.slice(from..to)}.map { Grid(it) }

    task1(grids)
    task2(grids)
}

private fun task1(grids: List<Grid>) {
    var columns = 0
    var rows = 0
    grids.forEach {grid ->
        // check vertical
        (1..<grid.width).forEach { divider ->
            val left = (divider - 1 downTo 0 ).map { grid.getCol(it) }
            val right = (divider..<grid.width).map { grid.getCol(it) }
            (left zip right).all { it.first == it.second }.also {
                if (it) {
                    columns += divider
                }
            }
        }
        // check horizontal
        (1..<grid.height).forEach { divider ->
            val top = (divider - 1 downTo 0 ).map { grid.getRow(it) }
            val bottom = (divider..<grid.height).map { grid.getRow(it) }
            (top zip bottom).all { it.first == it.second }.also {
                if (it) {
                    rows += divider
                }
            }
        }
    }
    println(rows * 100 + columns)
}

private fun differByOne(s1: String, s2: String): Boolean {
    return (s1 zip s2).count { it2 -> it2.first != it2.second } == 1
}

private fun task2(grids: List<Grid>) {
    var columns = 0
    var rows = 0
    grids.forEach { grid ->
        // check vertical
        (1..<grid.width).forEach { divider ->
            val left = (divider - 1 downTo 0 ).map { grid.getCol(it) }
            val right = (divider..<grid.width).map { grid.getCol(it) }
            if (differByOne(left.joinToString(""), right.joinToString(""))) {
                columns += divider
            }
        }
        // check horizontal
        (1..<grid.height).forEach { divider ->
            val top = (divider - 1 downTo 0 ).map { grid.getRow(it) }
            val bottom = (divider..<grid.height).map { grid.getRow(it) }
            if (differByOne(top.joinToString(""), bottom.joinToString(""))) {
                rows += divider
            }
        }
    }
    println(rows*100 + columns)
}