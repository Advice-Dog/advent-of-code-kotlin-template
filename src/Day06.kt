fun main() {

    fun getLanternfishCount(input: List<String>, days: Int): Long {
        val fishCount = mutableListOf<Long>()
        for (i in 0 until 9) {
            fishCount.add(0)
        }

        val list = input.first().split(",").map { it.toInt() }

        list.forEach {
            fishCount[it]++
        }

        println("Initial state: (${fishCount.sum()}) ${fishCount.joinToString { it.toString() }}")
        for (day in 1..days) {
            val children = fishCount[0]
            // add this amount of new fish to the end
            fishCount.add(children)
            // move this amount to 6 days from now
            fishCount[7] += children
            // clear the current day
            fishCount.removeAt(0)
            println("After day $day: (${fishCount.sum()}) ${fishCount.joinToString { it.toString() }}")
        }

        return fishCount.sum()
    }

    fun part1(input: List<String>): Long {
        return getLanternfishCount(input, days = 80)
    }

    fun part2(input: List<String>): Long {
        return getLanternfishCount(input, days = 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26_984_457_539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}