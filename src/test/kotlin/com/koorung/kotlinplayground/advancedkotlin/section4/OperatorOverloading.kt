package com.koorung.kotlinplayground.advancedkotlin.section4

import java.time.LocalDate


data class Point (
    val x: Int,
    val y: Int,
) {
    fun zeroPointSymmetry(): Point = Point(-x, -y)
    operator fun unaryMinus(): Point = Point(-x, -y)
    operator fun inc(): Point = Point(x + 1, y + 1)
}

data class Days (
    val day: Long
)

val Int.day: Days
    get() = Days(this.toLong())

operator fun LocalDate.plus(days: Days): LocalDate = this.plusDays(days.day)

enum class Operator (
    private val oper: Char,
    private val calcFun: (Int, Int) -> Int,
) {
    PLUS('+', {a, b -> a + b}),
    MINUS('-', {a, b -> a - b}),
    MULTIPLY('*', {a, b -> a * b}),
    DIVIDE('/', {a, b ->
        if (b == 0) throw IllegalArgumentException("0으로 나눌 수 없습니다") else a / b
    });

    operator fun invoke(a: Int, b: Int): Int = this.calcFun(a, b)
}