import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }

        val min = positions.minOf { it }
        val max = positions.maxOf { it }

        var best = -1

        for (target in min..max) {
            val sum = positions.sumOf { abs(target - it) }
            if (sum < best || best == -1)
                best = sum
        }

        return best
    }

    fun part2(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }

        val min = positions.minOf { it }
        val max = positions.maxOf { it }

        var best = -1
        for (target in min..max) {
            val sum = positions.sumOf { getFuelCost(abs(target - it)) }
            if (sum < best || best == -1)
                best = sum
        }

        return best
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

fun getFuelCost(distance: Int): Int {
    var temp = 0
    for (index in 1..distance) {
        temp += index
    }
    return temp
}