import kotlin.math.abs

fun main() {

    fun part1(input: List<String>, print: Boolean = false): Int {
        val map = input.flatMap {
            it.toPointsList(skipDiagonal = true)
        }.groupBy { it }.map { it.key to it.value.size }

        val sorted = map.sortedWith(compareBy({ it.first.x }, { it.first.y }))

        if (print) {
            val xMax = map.maxOf { it.first.x }
            val yMax = map.maxOf { it.first.y }


            println(sorted)
            for (y in 0..yMax) {
                for (x in 0..xMax) {
                    val element = map.find { it.first.x == x && it.first.y == y }
                    if (element != null)
                        print(element.second)
                    else
                        print(".")
                }
                println()
            }
        }
//        for (point in Point(0, 0)..Point(9, 9)) {
//            if (point.y == 0)
//                println()
//
//            val element = map.find { it.first == point }
//            if (element != null)
//                print(element.second)
//            else
//                print(".")
//        }

        val count = map.count { it.second >= 2 }
        println("Count: $count")
        println(map.count { it.second > 2 })
        return count
    }

    fun part2(input: List<String>): Int {
        val map = input.flatMap {
            it.toPointsList(skipDiagonal = false)
        }.groupBy { it }.map { it.key to it.value.size }

        return map.count { it.second >= 2 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput, print = true) == 5)
    //check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    //println(part2(input))
}

fun String.toPointsList(skipDiagonal: Boolean = false): List<Point> {
    val (first, last) = split("->").map { it.toPoint() }

    // Skip any diagonal lines
    if (skipDiagonal && first.x != last.x && first.y != last.y)
        return emptyList()

    val points = listOf(first) + (first..last).toList()
    val expectedX = abs(first.x - last.x)
    val expectedY = abs(first.y - last.y)
    if (points.size - 1 != expectedX && points.size - 1 != expectedY) {
        println(points)
        println("$expectedX, $expectedY")
        error("$first to $last generated wrong number of points: ${points.size}")
    }


    return points
}

fun String.toPoint(): Point {
    val list = split(",").map { it.trim().toInt() }
    return Point(list.first(), list.last())
}

class PointProgression(
    override val start: Point,
    override val endInclusive: Point,
    private val step: Int = 1
) : Iterable<Point>, ClosedRange<Point> {

    override fun iterator(): Iterator<Point> = PointIterator(start, endInclusive, step)

    infix fun step(step: Int) = PointProgression(start, endInclusive, step)

}

class PointIterator(startPoint: Point, private val endPointInclusive: Point, step: Int) : Iterator<Point> {

    private var x = 0
    private var y = 0

    private var currentPoint = startPoint

    init {
        x = when {
            endPointInclusive.x > startPoint.x -> step
            endPointInclusive.x < startPoint.x -> -step
            else -> 0
        }

        y = when {
            endPointInclusive.y > startPoint.y -> step
            endPointInclusive.y < startPoint.y -> -step
            else -> 0
        }
    }

    override fun hasNext(): Boolean {
        return currentPoint != endPointInclusive
    }

    override fun next(): Point {
        val next = currentPoint.next(x, y)
        currentPoint = next
        return next
    }
}

data class Point(val x: Int, val y: Int) : Comparable<Point> {
    override fun compareTo(other: Point): Int {
        if (x < other.x || y < other.y)
            return -1
        if (x > other.x || y > other.y)
            return 1
        return 0
    }

    operator fun rangeTo(other: Point) = PointProgression(this, other)
}

fun Point.next(x: Int, y: Int) = Point(this.x + x, this.y + y)
