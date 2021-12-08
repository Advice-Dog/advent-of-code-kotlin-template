import kotlin.math.abs

fun main() {

    fun part1(input: List<String>, print: Boolean = false): Int {
        val map = input.flatMap {
            it.toPointsList(skipDiagonal = true)
        }

        val group = map.groupBy { it }.map { it.key to it.value.size }


        var find = map.filter { it.x == 466 && it.y == 235 }
        println("$find -> ${find.size}")

        var find2 = map.groupBy { it }.entries.find { it.key.x == 466 && it.key.y == 235 }
        println("$find2 -> ${find2?.value?.size}")

        val sorted = group.sortedWith(compareBy({ it.first.x }, { it.first.y }))

        val xMax = group.maxOf { it.first.x }
        val yMax = group.maxOf { it.first.y }

        if (print) {
            val builder = StringBuilder()

            //println(sorted)
            for (y in 0..yMax) {
                for (x in 0..xMax) {
                    val element = group.find { it.first.x == x && it.first.y == y }
                    val character = element?.second ?: "."

                    //  print(character)
                    builder.append(character)
                }
                builder.appendLine()
                //println()
//                print(y)
            }


            println(builder.toString())
            // File("C:\\Users\\disen\\Documents\\GitHub\\advent-of-code-kotlin-template\\src\\day_05_result.txt").writeText(builder.toString())
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

        val frequencyMap: MutableMap<String, Int> = HashMap()


//        val count = group.count { it.second > 1 }
//        println("Count: $count")
//
//        var count2 = 0
//
        for (y in 0..yMax) {
            for (x in 0..xMax) {
                val s = "$x,$y"

//                println("BEFORE; ${frequencyMap[s]}")
                
                val get = frequencyMap[s]
                if (get != null) {
//                    println("NOT NULL: $get")
                    frequencyMap[s] = get + 1
                } else {

                    frequencyMap[s] = map.count { it.x == x && it.y == y }
                }

                println("$s -> ${frequencyMap[s]}")
                //val element = map.filter { it.x == x && it.y == y }
//                val element = map.count { it.x == x && it.y == y }
//                if(element > 1) {
//                    count2++
//                    println(count2)
//                }

//                println(s)
            }
        }
//
//        println("FINAL: " + count2)

        val count = frequencyMap.entries.count { it.value > 1 }

        println("FINAL: $count")
        return count
    }

    fun part2(input: List<String>): Int {
        val map = input.flatMap {
            it.toPointsList(skipDiagonal = false)
        }

        val xMax = map.maxOf { it.x }
        val yMax = map.maxOf { it.y }

        val frequencyMap: MutableMap<String, Int> = HashMap()


//        val count = group.count { it.second > 1 }
//        println("Count: $count")
//
//        var count2 = 0
//
        for (y in 0..yMax) {
            for (x in 0..xMax) {
                val s = "$x,$y"

//                println("BEFORE; ${frequencyMap[s]}")

                val get = frequencyMap[s]
                if (get != null) {
//                    println("NOT NULL: $get")
                    frequencyMap[s] = get + 1
                } else {

                    frequencyMap[s] = map.count { it.x == x && it.y == y }
                }

                println("$s -> ${frequencyMap[s]}")
                //val element = map.filter { it.x == x && it.y == y }
//                val element = map.count { it.x == x && it.y == y }
//                if(element > 1) {
//                    count2++
//                    println(count2)
//                }

//                println(s)
            }
        }
//
//        println("FINAL: " + count2)

        val count = frequencyMap.entries.count { it.value > 1 }

        println("FINAL: " + count)
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput, print = true) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
//    println(part1(input, print = false))
    //println(part1_alt(input))
    println(part2(input))
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

data class Point(val x: Int, val y: Int, var id: Int = index++) : Comparable<Point> {

    companion object {
        var index = 0
    }


    override fun compareTo(other: Point): Int {
        if (x < other.x || y < other.y)
            return -1
        if (x > other.x || y > other.y)
            return 1
        return 0
    }

    operator fun rangeTo(other: Point) = PointProgression(this, other)

    override fun equals(other: Any?): Boolean {
        if (other is Point)
            return x == other.x && y == other.y
        return false
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

fun Point.next(x: Int, y: Int) = Point(this.x + x, this.y + y)
