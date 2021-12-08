fun main() {

    fun getDepthIncreases(input: List<String>): Int {
        return input.windowed(2, 1)
            .count { it.last() > it.first() }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(getDepthIncreases(testInput) == 7)

    val input = readInput("Day01")
    println(getDepthIncreases(input))
}
