fun main() {

    fun getPowerConsumption(input: List<String>): Int {
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

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(getPowerConsumption(testInput) == 198)

    val input = readInput("Day03")
    println(getPowerConsumption(input))
}