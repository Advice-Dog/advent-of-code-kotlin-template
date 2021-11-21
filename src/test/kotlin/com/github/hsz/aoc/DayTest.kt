package com.github.hsz.aoc

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class DayTest {

    abstract val day: Day

    @AfterAll
    fun solve() {
        with(day) {
            println("Solutions for Day $number:")
            println("Part 1: ${part1()}")
            println("Part 2: ${part2()}")
        }
    }
}
