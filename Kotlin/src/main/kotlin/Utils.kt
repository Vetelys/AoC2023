import java.io.File
import java.math.BigInteger

fun readFile(fileName: String): List<String> {
    return File(fileName).readLines()
}

data class Coordinate(
    val x: Int,
    val y: Int
)

class Grid(private val strings: List<String>) {
    private val container = List(strings.size) { index -> strings[index].toList()}
    val width = strings.maxBy { it.length }
    val height = strings.size

    fun at(x: Int, y: Int): Char {
        return container[y][x]
    }

    operator fun get(c: Coordinate): Char {
        return container[c.y][c.x]
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