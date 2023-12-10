import java.io.File
import java.math.BigInteger

fun readFile(fileName: String): List<String> {
    return File(fileName).readLines()
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