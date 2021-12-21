fun main() {

    fun part1(input: List<String>): Int {
        val counts = Array(input.first().length) { 0 }

        for (binary in input) {
            for ((index, c) in binary.withIndex()) {
                counts[index] += c.digitToInt()
            }
        }

        val gammaRate = Integer.parseInt(counts.joinToString("") {
            if (it > input.size / 2) "1" else "0"
        }, 2)

        val epsilonRate = Integer.parseInt(counts.joinToString("") {
            if (it < input.size / 2) "1" else "0"
        }, 2)

        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val maxLength = input.first().length

        var index = 0
        var temp = input
        while (temp.size > 1 && index < maxLength) {
            val count = temp.count { it[index] == '1' }

            val common = if (count >= temp.size - count) {
                '1'
            } else {
                '0'
            }

            temp = temp.filter { it[index] == common }
            index++
        }

        val oxygenGenerationRate = Integer.parseInt(temp.first(), 2)

        index = 0

        temp = input
        while (temp.size > 1 && index < maxLength) {
            val count = temp.count { it[index] == '1' }

            val common = if (count >= temp.size - count) {
                '1'
            } else {
                '0'
            }

            temp = temp.filter { it[index] != common }
            index++
        }

        val co2ScrubberRating = Integer.parseInt(temp.first(), 2)

        return oxygenGenerationRate * co2ScrubberRating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = getTestData("Day03")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = getData("Day03")
    println(part1(input))
    println(part2(input))
}