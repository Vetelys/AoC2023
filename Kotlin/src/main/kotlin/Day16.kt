lateinit var initialGrid: Grid

fun main() {
    val input = readFile("../input16.txt")
    initialGrid = Grid(input)
    task1()
    task2()
}

private fun task1() {
    traverse(Coordinate(0,0), Direction.EAST).also { println(it) }
}

private fun traverseBeam(currentDirection: Direction, currentLocation: Coordinate, queue: ArrayDeque<Pair<Coordinate, Direction>>) {
    val currentSymbol = initialGrid[currentLocation]
    when (currentSymbol) {
        '.' -> queue.add(currentLocation to currentDirection)
        '|' -> when (currentDirection) {
            Direction.NORTH, Direction.SOUTH -> queue.add(currentLocation to currentDirection)
            Direction.WEST, Direction.EAST -> {
                queue.add(currentLocation to Direction.NORTH)
                queue.add(currentLocation to Direction.SOUTH)
            }
        }

        '/' -> when (currentDirection) {
            Direction.NORTH -> queue.add(currentLocation to Direction.EAST)
            Direction.SOUTH -> queue.add(currentLocation to Direction.WEST)
            Direction.EAST -> queue.add(currentLocation to Direction.NORTH)
            Direction.WEST -> queue.add(currentLocation to Direction.SOUTH)
        }

        '\\' -> when (currentDirection) {
            Direction.NORTH -> queue.add(currentLocation to Direction.WEST)
            Direction.SOUTH -> queue.add(currentLocation to Direction.EAST)
            Direction.EAST -> queue.add(currentLocation to Direction.SOUTH)
            Direction.WEST -> queue.add(currentLocation to Direction.NORTH)
        }

        '-' -> when (currentDirection) {
            Direction.WEST, Direction.EAST -> queue.add(currentLocation to currentDirection)
            Direction.NORTH, Direction.SOUTH -> {
                queue.add(currentLocation to Direction.WEST)
                queue.add(currentLocation to Direction.EAST)
            }
        }
    }
}

// For each beam, traverse through the initial grid and return the tiles that the beam energizes - at the end take the union of all the grids to find out total number of energized tiles
private fun traverse(start: Coordinate, startDirection: Direction): Int {
    val visited = hashSetOf<Pair<Coordinate, Direction>>()
    val queue = ArrayDeque<Pair<Coordinate, Direction>>()
    traverseBeam(startDirection, start, queue)
    while (queue.isNotEmpty()) {
        val (coord, direction) = queue.removeFirst()
        if (coord to direction in visited) continue
        visited.add(coord to direction)
        val nextCoord = initialGrid.nextCoordinate(coord, direction) ?: continue
        traverseBeam(direction, nextCoord, queue)
    }
    return visited.map { it.first }.distinct().count()
}

private fun task2() {
    var energizedTiles = mutableListOf<Int>()
    (0..<initialGrid.height).forEach {
        energizedTiles.add(traverse(Coordinate(x = 0, y = it), Direction.EAST))
        energizedTiles.add(traverse(Coordinate(x = initialGrid.width - 1, y = it), Direction.WEST))
    }
    (0..<initialGrid.width).forEach {
        energizedTiles.add(traverse(Coordinate(x = it, y = initialGrid.height - 1), Direction.NORTH))
        energizedTiles.add(traverse(Coordinate(x = it, y = 0), Direction.SOUTH))
    }
    println(energizedTiles.max())
}