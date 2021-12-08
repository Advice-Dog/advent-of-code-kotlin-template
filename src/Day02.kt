fun main() {

    fun part1(input: List<Command>): Int {
        val horizontal = input
            .filterIsInstance<Command.Forward>()
            .sumOf { it.value }

        val depth = input
            .filterNot { it is Command.Forward }
            .sumOf { it.value }

        return horizontal * depth
    }

    fun part2(input: List<Command>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        input.forEach {
            when (it) {
                is Command.Forward -> {
                    horizontal += it.value
                    depth += aim * it.value
                }
                is Command.Up,
                is Command.Down -> {
                    aim += it.value
                }
            }
        }

        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
        .map { it.toCommand() }
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
        .map { it.toCommand() }
    println(part1(input))
    println(part2(input))
}

sealed class Command(val value: Int) {
    class Forward(value: Int) : Command(value)
    class Down(value: Int) : Command(value)
    class Up(value: Int) : Command(-value)
}

fun String.toCommand(): Command {
    val items = split(" ")

    val command = items.first()
    val value = items.last().toInt()

    return when (command) {
        "forward" -> Command.Forward(value)
        "up" -> Command.Up(value)
        "down" -> Command.Down(value)
        else -> error("unknown command type: $command")
    }
}