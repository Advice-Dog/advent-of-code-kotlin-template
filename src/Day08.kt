fun main() {

    val zero = 6
    val one = 2
    val two = 5
    val three = 5
    val four = 4
    val five = 5
    val six = 6
    val seven = 3
    val eight = 7
    val nine = 6

    val unique = listOf(one, four, seven, eight)

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (input, output) = it.split("|")
            output.split(" ").count { it.length in unique }
        }
    }

    fun part2(input: List<String>): Int {
        val it = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"
        val (input, output) = it.split("|")
//         0000
//        1    2
//        1    2
//         3333
//        4    5
//        4    5
//         6666
        val map = mutableMapOf<Int, String>()
        for (i in 0 until 7) {
            map[i] = ""
        }

        val split = input.split(" ")
            .map { it.toSortedSet().joinToString(separator = "") { it.toString() } }//.filter { it.length in unique }
        println(split)

        split.forEach {
            when (it.length) {
                one -> {
                    map[2] += it
                    map[5] += it
                }
                four -> {
                    map[1] += it
                    map[3] += it
                    map[5] += it
                }
                seven -> {
                    map[0] += it
                    map[2] += it
                    map[5] += it
                }
            }
        }
        println(map)

        val o = split.find { it.length == one }!!
        val f = split.find { it.length == four }!!
        val s = split.find { it.length == seven }!!

        // x is 0
        val x = s.filter { it !in o }

        map[0] = x
        map[2] = o
        map[5] = o

        val f2 = f.filter { it !in o }
        map[1] = f2
        map[3] = f2


        // 2, 3, 5 all have 5 segments
        var new_three = split.find { it.length == three && it.contains(o) }!!
        println(new_three)
        // these two items are for the 3rd and 6th position
        new_three = new_three.filter { it !in s }
        println(new_three)

        println(f2)
        val middleCharacter = new_three.find { it in f2 }.toString()
        map[3] = middleCharacter
        val firstCharacter = new_three.find { it !in f2 }.toString()
        map[6] = firstCharacter

        map[1] = f2.find { it.toString() != middleCharacter }.toString()

        // find 8 and see what 4th position is
        val e = split.find { it.length == eight }!!
        println("eight: $e")
        val e_find = e.find { character -> !map.any { character in it.value } }
        println(e_find)
        map[4] = e_find.toString()

        // find 2 and see what 2nd position is, use that to set 2nd and 5th position
        val one_options = map[2]!!
        val two_find = split.find { s1 -> s1.length == two && one_options !in s1 && map[1]!! !in s1 }!!
        println("two find: $two_find does not contain $one_options")
        map[2] = two_find.filter { it in one_options }
        map[5] = one_options.filter { it !in map[2]!! }

//        map.forEach {
//            map[it.key] = it.value.split("").distinct().filter { it.isNotBlank() }.joinToString(separator = "") { it }
//        }
//
//        println(map)
//
//        map.forEach {
//            val key = it.key
//            println("Looking for $it")
//            val find = it.value.find { character -> map.all { it.key == key || character !in it.value } }
//            println("Found: $find not in any others")
//
//            //map[it.key] = it.value.split("").distinct().filter { it.isNotBlank() }.toString()
//        }

        val builder = StringBuilder()
        builder.append(" " + (map[0]?.repeat(4) ?: "....") + "\n")
        builder.append((map[1] ?: ".") + "    " + (map[2] ?: ".") + "\n")
        builder.append((map[1] ?: ".") + "    " + (map[2] ?: ".") + "\n")
        builder.append(" " + (map[3]?.repeat(4) ?: "....") + "\n")
        builder.append((map[4] ?: ".") + "    " + (map[5] ?: ".") + "\n")
        builder.append((map[4] ?: ".") + "    " + (map[5] ?: ".") + "\n")
        builder.append(" " + (map[6]?.repeat(4) ?: "....") + "\n")

        println(builder.toString())

        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 5353)

    val input = readInput("Day08")
    println(part1(input))
//    println(part2(input))
}
