import java.io.File

lateinit var grid: Grid
lateinit var startPoint: Coordinate

fun main() {
    val input = readFile("../test.txt")
    grid = Grid(input)
    startPoint = grid.find('S') ?: return
    val distances = task1()
    println(distances.values.max())
    task2(distances.keys.toList())
}

private fun possibleConnections(c: Char?): List<Direction> {
    return when(c) {
        '|' -> listOf(Direction.NORTH, Direction.SOUTH)
        '-' -> listOf(Direction.EAST, Direction.WEST)
        'L' -> listOf(Direction.NORTH, Direction.EAST)
        'J' -> listOf(Direction.NORTH, Direction.WEST)
        '7' -> listOf(Direction.SOUTH, Direction.WEST)
        'F' -> listOf(Direction.SOUTH, Direction.EAST)
        else -> emptyList()
    }
}

private fun determineStartType(): Char {
    val north = possibleConnections(grid.nextCharTo(startPoint, Direction.NORTH))
    val south = possibleConnections(grid.nextCharTo(startPoint, Direction.SOUTH))
    val east = possibleConnections(grid.nextCharTo(startPoint, Direction.EAST))
    val west = possibleConnections(grid.nextCharTo(startPoint, Direction.WEST))
    return when {
        north.contains(Direction.SOUTH) && east.contains(Direction.WEST) -> 'L'
        north.contains(Direction.SOUTH) && west.contains(Direction.EAST) -> 'J'
        south.contains(Direction.NORTH) && west.contains(Direction.EAST) -> '7'
        south.contains(Direction.NORTH) && east.contains(Direction.WEST) -> 'F'
        north.contains(Direction.SOUTH) && south.contains(Direction.NORTH) -> '|'
        west.contains(Direction.EAST) && east.contains(Direction.WEST) -> '-'
        else -> '.'
    }
}

private fun canMoveToDir(c: Coordinate, d: Direction): Boolean {
    val next = possibleConnections(grid.nextCharTo(c, d))
    return when {
        d == Direction.NORTH && next.contains(Direction.SOUTH) -> true
        d == Direction.SOUTH && next.contains(Direction.NORTH) -> true
        d == Direction.EAST && next.contains(Direction.WEST) -> true
        d == Direction.WEST && next.contains(Direction.EAST) -> true
        else -> false

    }
}


private fun task1(): LinkedHashMap<Coordinate, Int> {
    val startType = determineStartType()
    val visited = hashSetOf(startPoint)
    val distances = linkedMapOf<Coordinate, Int>()
    val queue = ArrayDeque<Pair<Coordinate, Int>>()
    val startNeighbors = possibleConnections(startType).mapNotNull { grid.nextCoordinate(startPoint, it) }
    queue.addAll(startNeighbors.map { it to 1 })
    visited.add(startPoint)
    distances[startPoint] = 0
    while (queue.isNotEmpty()) {
        val (current, level) = queue.removeFirst()
        distances[current] = level

        val nextPossibleDirections = possibleConnections(grid[current]).filter { canMoveToDir(current, it) }
        nextPossibleDirections.map { grid.nextCoordinate(current, it) }.mapNotNull { it }.forEach {
            if (it !in visited) {
                queue.add(it to level + 1)
                visited.add(it)
            }
        }
    }
    return distances
}

private fun task2(
    visited: List<Coordinate>
) {
}
