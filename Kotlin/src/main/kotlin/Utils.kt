import java.io.File
import java.math.BigInteger

fun readFile(fileName: String): List<String> {
    return File(fileName).readLines()
}

data class Coordinate(
    val x: Int,
    val y: Int
)

// Should probably be an array instead
class Grid(private val strings: List<String>) {
    private var container = List(strings.size) { index -> strings[index].toList()}
    val width = strings[0].length
    val height = strings.size

    fun at(x: Int, y: Int): Char {
        return container[y][x]
    }

    // swap rows and cols
    fun transpose(): Grid {
        val strings = mutableListOf<String>()
        (0..<width).forEach { x ->
            var string = ""
            (0..<height).forEach { y ->
                string += container[y][x]
            }
            strings.add(string)
        }
        return Grid(strings)
    }

    fun flip(): Grid {
        return Grid(container.reversed().map { it.joinToString("") })
    }

    operator fun get(c: Coordinate): Char {
        return container[c.y][c.x]
    }

    fun getCol(x: Int): String {
        var col = ""
        (0..<height).forEach { y ->
            col += container[y][x]
        }
        return col
    }

    override fun toString(): String {
        return container.toString()
    }

    fun getRow(y: Int): String {
        var row = ""
        (0..<width).forEach { x ->
            row += container[y][x]
        }
        return row
    }
}

/*
LCM from here: https://www.baeldung.com/kotlin/lcm - modified to use BigInteger
*/

private fun findLCM(a: BigInteger, b: BigInteger): BigInteger {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == BigInteger.ZERO && lcm % b == BigInteger.ZERO) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun LCM(numbers: List<Int>): BigInteger {
    var result = numbers[0].toBigInteger()
    for (i in 1 until numbers.size) {
        result = findLCM(result, numbers[i].toBigInteger())
    }
    return result
}