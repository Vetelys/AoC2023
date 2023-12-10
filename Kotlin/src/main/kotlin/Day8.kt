import java.math.BigInteger

private data class Node(
    val right: String,
    val left: String,
)

private val Routes = HashMap<String, Node>()
private const val Goal = "ZZZ"
private const val Start = "AAA"

fun main() {
    val input = readFile("../input8.txt")
    input.drop(2).forEach {
        val start = it.substring(0, 3)
        val left = it.substring(7, 10)
        val right = it.substring(12, 15)
        Routes[start] = Node(right, left)
    }
    task1(input)
    task2(input)
}

private fun task1(input: List<String>): Int {
    val instructions = input[0]
    var currentLocation = Start
    var steps = 0
    while (currentLocation != Goal) {
        instructions.forEach {
            currentLocation = if (it == 'L') {
                Routes[currentLocation]!!.left
            } else {
                Routes[currentLocation]!!.right
            }
            steps += 1
            if (currentLocation == Goal) {
                return@forEach
            }
        }
    }
    println("steps: $steps")
    return steps
}

private fun task2(input: List<String>) {
    val instructions = input[0]
    val startNodes = getStartNodes()
    startNodes.map {
        var currentLocation = it
        var steps = 0
        while (!currentLocation.endsWith('Z')) {
            instructions.forEach {
                currentLocation = if (it == 'L') {
                    Routes[currentLocation]!!.left
                } else {
                    Routes[currentLocation]!!.right
                }
                steps += 1
                if (currentLocation.endsWith('Z')) {
                    return@forEach
                }
            }
        }
        steps
    }.also { println("steps: ${LCM(it)}") }
}

private fun getStartNodes(): MutableList<String> {
    return Routes.keys.filter { it.endsWith('A') }.toMutableList()
}


