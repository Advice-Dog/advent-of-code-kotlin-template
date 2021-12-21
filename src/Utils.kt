import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun getTestData(name: String) = File("src/mock_data", "$name.txt").readLines()

fun getData(name: String) = File("src/data", "$name.txt").readLines()

open class CodeRunner(private val day: String) {

    open val part1Result: Long = -1
    open val part2Result: Long = -1

    open fun part1(input: List<String>): Int {
        TODO("Not yet implemented")
    }

    open fun part2(input: List<String>): Int {
        TODO("Not yet implemented")
    }

    init {
        run()
    }

    private fun run() {
        // test if implementation meets criteria from the description, like:
        val testInput = getMockData(day)
        try {
            check(part1(testInput).toLong() == part1Result)
        } catch (ex: NotImplementedError) {
            // part 1 not implemented yet
        }

        try {
            check(part2(testInput).toLong() == part2Result)
        } catch (ex: NotImplementedError) {
            // part 2 not implemented yet
        }

        val input = getData(day)
        try {
            println(part1(input))
        } catch (ex: NotImplementedError) {
            // part 1 not implemented yet
        }

        try {
            println(part2(input))
        } catch (ex: NotImplementedError) {
            // part 2 not implemented yet
        }
    }

    // To allow easily passing in other data
    open fun getMockData(name: String) = getTestData(day)
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
