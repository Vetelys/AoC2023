fun main() {
    val input = readFile("../input14.txt")
    task1(input)
    task2(input)
}

private const val NR_CYCLES = 1000000000

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
}

private fun task1(input: List<String>) {
    val grid = Grid(input)
    val tiltedGrid = tiltGrid(grid, Direction.NORTH)
    calculateLoad(tiltedGrid).also { println(it) }
}


private fun calculateLoad(grid: Grid): Int {
    return (0..<grid.width)
        .map { grid.getCol(it).toList() }
        .sumOf {
            it.foldIndexed(0) { index: Int, acc: Int, c: Char ->
                acc + (grid.height - index) * (if (c == 'O') 1 else 0)
            }
        }
}
private fun tiltGrid(grid: Grid, direction: Direction): Grid {
    return when (direction) {
        Direction.NORTH -> Grid((0..<grid.width).map { moveRocksToEnd(grid.getCol(it)) }).transpose()
        Direction.SOUTH -> Grid((0..<grid.width).map { moveRocksToEnd(grid.getCol(it).reversed())}).transpose().flip()
        Direction.WEST -> Grid((0..<grid.height).map { moveRocksToEnd(grid.getRow(it))})
        Direction.EAST -> Grid((0..<grid.height).map { moveRocksToEnd(grid.getRow(it).reversed())}.map { it.reversed() })
    }
}

private fun moveRocksToEnd(s: String): String {
    var lastStaticRock = -1
    return s.foldIndexed("") { index, acc: String, c: Char ->
        when (c) {
            'O' -> {
                acc.substring(0, lastStaticRock+1) + 'O' + acc.substring(minOf(lastStaticRock + 1, acc.length)).also {
                    lastStaticRock += 1
                }
            }
            '.' -> "$acc."
            else -> {
                lastStaticRock = index
                "$acc#"
            }
        }
    }
}

private fun task2(input: List<String>) {
    val grids = mutableListOf<Grid>()
    val seen = mutableListOf<String>()
    var grid = Grid(input)
    val previousMatch: Int
    val cycleLength: Int
    var i = 0
    while(true) {
        grid = tiltGrid(grid, Direction.NORTH)
        grid = tiltGrid(grid, Direction.WEST)
        grid = tiltGrid(grid, Direction.SOUTH)
        grid = tiltGrid(grid, Direction.EAST)
        val entry = grid.toString()
        seen.add(entry)
        grids.add(grid)
        if (seen.count { it == entry } > 1) {
            previousMatch = seen.indexOfFirst { it == entry }
            cycleLength = i - previousMatch
            println("Duplicate entry at $i - first at ${previousMatch}")
            println("Cycle length $cycleLength")
            break
        }
        i+=1
    }
    val indexOfMatch = previousMatch + (NR_CYCLES - previousMatch) % cycleLength - 1
    println(calculateLoad(grids[indexOfMatch]))
}