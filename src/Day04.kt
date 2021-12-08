fun main() {

    fun part1(input: Bingo): Int {
        input.numbers.forEach {

        }

        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
        .toBoard()

    check(part1(testInput) == 4512)

    val input = readInput("Day04")
        .toBoard()

    println(part1(input))
}

fun List<String>.toBoard(): Bingo {
    val numbers = this.first().split(",").map { it.toInt() }

    println(numbers)

    val windowed = this.subList(2, this.size)
        .windowed(5, 6)

    println(windowed)

    val map = windowed.map {
        it.map {
            val split = it.split(" ")
                .filter { it.isNotBlank() }
            split.map { it.toInt() }
        }
    }

    println(map)

    val list = map.map {
        Board(it.map { it.map { BoardPosition(it) } })
    }

    print(list)

    return Bingo(numbers, list)
}

data class Bingo(val numbers: List<Int>, val boards: List<Board>)

data class Board(val positions: List<List<BoardPosition>>)

data class BoardPosition(val value: Int, val hasBeenCalled: Boolean = false)