fun main() {

    fun part1(input: List<String>): Int {
        val board = input.toBoard()
        board.numbers.forEach {
            println("Calling number: $it")

            board.call(it)

            val bingo = board.boards.find { it.hasBingo() }
            if (bingo != null) {
                println("BINGO")
                val score = bingo.getScore() * it
                println("score: $score")
                return score
            }
        }

        return -1
    }

    fun part2(input: List<String>): Int {
        val board = input.toBoard()

        val boards = board.boards.toMutableList()
        var score = -1

        board.numbers.forEach {
            println("Calling number: $it")
            println("boards: ${boards.size}, score: $score")
            board.call(it)
            val bingos = boards.filter { it.hasBingo() }
            if (bingos.isNotEmpty()) {
                for (bingo in bingos) {
                    println("BINGO\n$bingo")
                    score = bingo.getScore() * it
                    boards.remove(bingo)
                }
            }
        }


        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = getTestData("Day04")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = getData("Day04")
    println(part1(input))
    println(part2(input))
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

data class Bingo(val numbers: List<Int>, val boards: List<Board>) {
    fun call(number: Int) {
        boards.forEach { it.call(number) }
    }
}

data class Board(val positions: List<List<BoardPosition>>) {
    fun call(number: Int) {
        positions.forEach { it.forEach { it.call(number) } }
    }

    fun hasBingo(): Boolean {
        val any = positions.any { it.all { it.hasBeenCalled } }
        if (any)
            return true

        for (i in 0..4)
            if (positions.all { it.elementAt(i).hasBeenCalled }) {
                return true
            }

        return false
    }

    fun getScore(): Int {
        return positions.flatten()
            .filter { !it.hasBeenCalled }
            .sumOf { it.value }
    }

    override fun toString(): String {
        return positions.joinToString(separator = "\n") {
            it.joinToString(separator = " ") { it.toString() }
        }
        //return super.toString()
    }
}

data class BoardPosition(val value: Int, var hasBeenCalled: Boolean = false) {
    fun call(number: Int) {
        if (value == number)
            hasBeenCalled = true
    }

    override fun toString(): String {
        return if (hasBeenCalled)
            "."
        else
            return value.toString()
    }
}