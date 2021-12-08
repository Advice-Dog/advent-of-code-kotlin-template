fun main() {

    fun part1(input: List<String>): Int {
        val list = input.first().toLanternfish().toMutableList()

        println("Initial state: $list")

        for (day in 1..80) {
            val children = mutableListOf<Lanternfish>()

            list.forEach {
                it.tick()

                if (it.hasChild) {
                    children.add(Lanternfish())
                    it.reset()
                }
            }

            list.addAll(children)

            println("After day $day: ${list.size} -> $list")
        }

        val size = list.size
        println("size: $size")
        return size
    }

    fun part2(input: List<String>): Long {
        val list = input.first().toLanternfish().toMutableList()

        println("Initial state: $list")

        for (day in 1..256) {
            val children = mutableListOf<Lanternfish>()

            list.forEach {
                it.tick()

                if (it.hasChild) {
                    children.add(Lanternfish())
                    it.reset()
                }
            }

            list.addAll(children)

            println("After day $day: ${list.size} -> $list")
        }

        val size = list.size
        println("size: $size")
        return size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26_984_457_539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

fun String.toLanternfish(): List<Lanternfish> {
    return split(",").map {
        Lanternfish(it.toInt())
    }
}

data class Lanternfish(var internal: Int = 8) {

    val hasChild: Boolean
        get() = internal == -1

    fun tick() = internal--

    fun reset() {
        internal = 6
    }

    override fun toString(): String {
        return internal.toString()
    }
}