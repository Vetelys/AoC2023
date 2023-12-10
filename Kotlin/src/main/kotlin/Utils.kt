import java.io.File

fun readFile(fileName: String): List<String> {
    return File(fileName).readLines()
}