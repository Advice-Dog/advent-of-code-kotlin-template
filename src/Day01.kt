fun main() {

    object : CodeRunner("Day01") {

        override val part1Result: Long
            get() = 7

        override val part2Result: Long
            get() = 5

        override fun part1(input: List<String>): Int {
            return input
                .map { it.toInt() }
                .windowed(2, 1)
                .count { it.last() > it.first() }
        }

        override fun part2(input: List<String>): Int {
            return input
                .asSequence()
                .map { it.toInt() }
                .windowed(3, 1)
                .map { it.sum() }
                .windowed(2, 1)
                .count { it.last() > it.first() }
        }
    }
}
